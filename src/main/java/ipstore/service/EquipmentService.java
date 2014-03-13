package ipstore.service;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ipstore.aspect.Action;
import ipstore.controller.ImportResultType;
import ipstore.controller.equipment.PasswordStatus;
import ipstore.controller.equipment.Status;
import ipstore.controller.equipment.TelnetStatus;
import ipstore.entity.Equipment;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import static ipstore.aspect.ActionType.EQUIPMENT_CREATE;
import static ipstore.aspect.change.ChangeType.EQUIPMENT;
import static ipstore.aspect.change.ChangeMode.CREATE;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Service("equipmentService")
@Transactional
public class EquipmentService {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Equipment> loadEquipments() {
        logger.debug("Start loading equipment.");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Equipment.class);
        criteria.add(Restrictions.ne("status", Status.DELETED));
        return criteria.addOrder(Order.asc("ipAddress")).list();
    }

    public Equipment loadEquipment(Long id) {
        logger.info("loading equipment with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        return (Equipment) session.get(Equipment.class, id);
    }

    public Equipment loadEquipment(String ipAddress) {
        logger.info("loading equipment with ipAddress=" + ipAddress);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Equipment.class);
        criteria.add(Restrictions.eq("ipAddress", ipAddress));
        return (Equipment) criteria.uniqueResult();
    }

    @Action(value = EQUIPMENT_CREATE, changeType = EQUIPMENT, changeMode = CREATE)
    public Long createEquipment(Equipment equipment, MultipartFile config) {
        logger.info("saving new equipment...");
        Session session = sessionFactory.getCurrentSession();
        equipment.setPasswordStatus(PasswordStatus.NEW);
        equipment.setPasswordDate(new Date());
        equipment.setStatus(Status.ACTIVE);
        equipment.setConfigName(config.getOriginalFilename());
        equipment.setConfigType(config.getContentType());
        try {
            equipment.setConfigData(config.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (Long) session.save(equipment);
    }

    public void updateEquipment(Long id, Equipment equipment, MultipartFile config, boolean reset) {
        logger.info("updating equipment with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Equipment oldEquipment = (Equipment) session.get(Equipment.class, id);
        oldEquipment.setIpAddress(equipment.getIpAddress());
        oldEquipment.setType(equipment.getType());
        oldEquipment.setUsername(equipment.getUsername());
        oldEquipment.setLogin(equipment.getLogin());
        oldEquipment.setClientName(equipment.getClientName());
        oldEquipment.setPlacementAddress(equipment.getPlacementAddress());
        oldEquipment.setApplicationNumber(equipment.getApplicationNumber());
        oldEquipment.setDescription(equipment.getDescription());
        oldEquipment.setTelnetCheck(equipment.getTelnetCheck());
        String oldPassword = oldEquipment.getPassword();
        String newPassword = equipment.getPassword();
        oldEquipment.setPassword(newPassword);
        if (ObjectUtils.notEqual(oldPassword, newPassword)) {
            oldEquipment.setPasswordStatus(PasswordStatus.NEW);
            oldEquipment.setPasswordDate(new Date());
        }
        if (reset && config.isEmpty()) {
            oldEquipment.setConfigData(null);
            oldEquipment.setConfigName(null);
            oldEquipment.setConfigType(null);
        } else if (!config.isEmpty()) {
            try {
                oldEquipment.setConfigData(config.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            oldEquipment.setConfigName(config.getOriginalFilename());
            oldEquipment.setConfigType(config.getContentType());
        }
    }

    public Equipment deleteEquipment(Long id) {
        logger.info("delete equipment with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Equipment equipment = (Equipment) session.get(Equipment.class, id);
        equipment.setStatus(Status.DELETED);
        return equipment;
    }

    public Equipment activateEquipment(Long id) {
        logger.info("activate deleted equipment with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Equipment equipment = (Equipment) session.get(Equipment.class, id);
        equipment.setStatus(Status.ACTIVE);
        return equipment;
    }

    public Equipment activateWithNoExpiredEquipment(Long id) {
        logger.info("activate with no expired password equipment with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Equipment equipment = (Equipment) session.get(Equipment.class, id);
        equipment.setStatus(Status.ACTIVE_NO_EXPIRED);
        equipment.setPasswordStatus(PasswordStatus.NEW);
        return equipment;
    }

    public void updatePasswordStatus(Long id, PasswordStatus passwordStatus) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("UPDATE Equipment SET passwordStatus = :status WHERE id = :id")
                .setParameter("status", passwordStatus)
                .setParameter("id", id)
                .executeUpdate();
    }

    public void updateTelnetStatus(Long id, TelnetStatus telnetStatus) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("UPDATE Equipment SET telnetStatus = :telnetStatus, telnetStatusDate = :telnetDate  WHERE id = :id")
                .setParameter("telnetStatus", telnetStatus)
                .setParameter("telnetDate", new Date())
                .setParameter("id", id)
                .executeUpdate();
    }

    public ImportResultType<Equipment> importFile(InputStream is) throws IOException, InvalidFormatException {
        ImportResultType<Equipment> result = new ImportResultType<Equipment>();
        Workbook wb = WorkbookFactory.create(is);
        Sheet sheet = wb.getSheetAt(0);
        int i = 0;
        for (Row row : sheet) {
            i++;
            if (i > 1) {
                Equipment equipment = makeEquipment(row);
                Session session = sessionFactory.getCurrentSession();
                Equipment oldEquipment = (Equipment) session.createCriteria(Equipment.class)
                        .add(Restrictions.eq("ipAddress", equipment.getIpAddress()))
                        .uniqueResult();
                if (oldEquipment != null) {
                    result.addExists(oldEquipment);
                } else {
                    Long id = (Long) session.save(equipment);
                    equipment.setId(id);
                    result.addAdded(equipment);
                }
            }
        }
        return result;
    }

    public HttpEntity<byte[]> loadConfig(Long id) {
        logger.info("load config for equipment with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Equipment equipment = (Equipment) session.get(Equipment.class, id);
        byte[] data = equipment.getConfigData();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType(equipment.getConfigType()));
        String filename = equipment.getConfigName().replace(" ", "_");
        try {
            filename = URLEncoder.encode(filename, "UTF8");
        } catch (UnsupportedEncodingException ignored) {
        }
        header.set("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
        header.setContentLength(data.length);
        return new HttpEntity<byte[]>(data, header);
    }

    private Equipment makeEquipment(Row row) {
        Equipment equipment = new Equipment();
        for (int j = 0; j <= 8; j++) {
            Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String value = cell.getStringCellValue();
            switch (j) {
                case 0:
                    equipment.setIpAddress(value);
                    break;
                case 1:
                    equipment.setType(value);
                    break;
                case 2:
                    equipment.setUsername(value);
                    break;
                case 3:
                    equipment.setLogin(value);
                    break;
                case 4:
                    equipment.setPassword(value);
                    break;
                case 5:
                    equipment.setClientName(value);
                    break;
                case 6:
                    equipment.setPlacementAddress(value);
                    break;
                case 7:
                    equipment.setApplicationNumber(value);
                    break;
                case 8:
                    equipment.setDescription(value);
                    break;
            }
        }
        equipment.setPasswordStatus(PasswordStatus.NEW);
        equipment.setPasswordDate(new Date());
        equipment.setStatus(Status.ACTIVE);
        return equipment;
    }
}
