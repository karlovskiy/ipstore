package utilits.quartz;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import utilits.controller.equipment.PasswordStatus;
import utilits.entity.Equipment;
import utilits.service.EquipmentService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class PasswordStatusJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(PasswordStatusJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap params = context.getMergedJobDataMap();
        EquipmentService equipmentService = (EquipmentService) params.get("equipmentService");
        int oldDays = Integer.parseInt((String) params.get("oldDays"));
        int needUpdateDays = Integer.parseInt((String) params.get("needUpdateDays"));
        logger.info("Start equipment status updating, oldDays=" + oldDays + ", needUpdateDays=" + needUpdateDays);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -oldDays);
        Date oldDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -(needUpdateDays - oldDays));
        Date needUpdateDate = calendar.getTime();
        List<Equipment> equipments = equipmentService.loadEquipments();
        for (Equipment equipment : equipments) {
            Date passwordDate = equipment.getPasswordDate();
            if (passwordDate.after(oldDate)) {
                changePasswordType(equipmentService, equipment, PasswordStatus.NEW);
            } else if (passwordDate.before(oldDate) && passwordDate.after(needUpdateDate)) {
                changePasswordType(equipmentService, equipment, PasswordStatus.OLD);
            } else {
                changePasswordType(equipmentService, equipment, PasswordStatus.NEED_UPDATE);
            }
        }

    }

    /**
     * @param equipmentService service
     * @param equipment        equipment
     * @param passwordStatus   password status
     */
    private void changePasswordType(EquipmentService equipmentService, Equipment equipment, PasswordStatus passwordStatus) {
        PasswordStatus oldPasswordStatus = equipment.getPasswordStatus();
        if (passwordStatus != oldPasswordStatus) {
            Long equipmentId = equipment.getId();
            logger.info("Updating password status from '" + oldPasswordStatus.name() + "' to '" +
                    passwordStatus.name() + "' for equipment with id=" + equipmentId);
            equipmentService.updatePasswordStatus(equipmentId, passwordStatus);
        }
    }
}
