package utilits.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilits.entity.Equipment;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Service("equipmentService")
@Transactional
public class EquipmentService {

    protected static Logger logger = Logger.getLogger(EquipmentService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Equipment> getEquipments(String search) {
        logger.debug("Start loading equipment, search string: " + search);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Equipment.class);
        if (StringUtils.isNotEmpty(search)) {
            criteria.add(Restrictions.like("ipAddress", "%" + search + "%"));
        }
        return criteria.addOrder(Order.asc("ipAddress")).list();
    }

    public Equipment getEquipment(Long id) {
        logger.info("loading equipment with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        return (Equipment) session.get(Equipment.class, id);
    }

    public Long saveEquipment(Equipment equipment) {
        logger.info("saving new equipment...");
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(equipment);
    }

    public void updateEquipment(Long id, Equipment equipment) {
        logger.info("updating equipment with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Equipment oldEquipment = (Equipment) session.get(Equipment.class, id);
        oldEquipment.setIpAddress(equipment.getIpAddress());
        oldEquipment.setType(equipment.getType());
        oldEquipment.setUsername(equipment.getUsername());
        oldEquipment.setLogin(equipment.getLogin());
        oldEquipment.setPassword(equipment.getPassword());
        oldEquipment.setClientName(equipment.getClientName());
        oldEquipment.setPlacementAddress(equipment.getPlacementAddress());
        oldEquipment.setApplicationNumber(equipment.getApplicationNumber());
        oldEquipment.setDescription(equipment.getDescription());
    }

    public void deleteEquipment(Long id) {
        logger.info("delete equipment with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Equipment equipment = (Equipment) session.get(Equipment.class, id);
        session.delete(equipment);
    }

    public boolean importFile(InputStream is) {
        try {
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            int i = 0;
            for (Row row : sheet) {
                i++;
                if (i > 1) {
                    Equipment equipment = new Equipment();
                    for (int j = 0; j <= 8; j++) {
                        Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
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
                    Session session = sessionFactory.getCurrentSession();
                    Equipment oldEquipment = (Equipment) session.createCriteria(Equipment.class)
                            .add(Restrictions.eq("ipAddress", equipment.getIpAddress()))
                            .uniqueResult();
                    if (oldEquipment != null) {
                        oldEquipment.setType(equipment.getType());
                        oldEquipment.setUsername(equipment.getUsername());
                        oldEquipment.setLogin(equipment.getLogin());
                        oldEquipment.setPassword(equipment.getPassword());
                        oldEquipment.setClientName(equipment.getClientName());
                        oldEquipment.setPlacementAddress(equipment.getPlacementAddress());
                        oldEquipment.setApplicationNumber(equipment.getApplicationNumber());
                        oldEquipment.setDescription(equipment.getDescription());
                    } else {
                        session.save(equipment);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("Importiong error...", e);
            return false;
        }
    }
}
