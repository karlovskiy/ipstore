package utilits.service;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    public List<Equipment> getEquipment() {
        logger.debug("Start loading equipment...");
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Equipment.class).list();
    }

    public boolean importFile(InputStream is) {
        try {
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            int i = 0;
            for (Row row : sheet) {
                i++;
                if (i > 1) {
                    int j = 0;
                    Equipment equipment = new Equipment();
                    for (Cell cell : row) {
                        String value = cell.getStringCellValue();
                        switch (j) {
                            case 0:
                                equipment.setIpAddress(value);
                                break;
                            case 1:
                                equipment.setType(value);
                                break;
                            case 2:
                                equipment.setPassword(value);
                                break;
                            case 3:
                                equipment.setPlacementAddress(value);
                                break;
                            case 4:
                                equipment.setDescription(value);
                                break;
                        }
                        j++;
                    }
                    Session session = sessionFactory.getCurrentSession();
                    Equipment oldEquipment = (Equipment) session.createCriteria(Equipment.class)
                            .add(Restrictions.eq("ipAddress", equipment.getIpAddress()))
                            .uniqueResult();
                    if (oldEquipment != null) {
                        oldEquipment.setType(equipment.getType());
                        oldEquipment.setPassword(equipment.getPassword());
                        oldEquipment.setPlacementAddress(equipment.getPlacementAddress());
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
