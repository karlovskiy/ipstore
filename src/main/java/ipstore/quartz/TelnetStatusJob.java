package ipstore.quartz;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import ipstore.controller.equipment.TelnetStatus;
import ipstore.entity.Equipment;
import ipstore.quartz.telnet.TelnetConnection;
import ipstore.quartz.telnet.TelnetConnectionFactory;
import ipstore.service.EquipmentService;

import java.util.List;

import static java.util.concurrent.TimeUnit.*;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.1, 9/5/13
 */
public class TelnetStatusJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(TelnetStatusJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Telnet equipment status checking started...");
        try {
            long startTime = System.currentTimeMillis();
            JobDataMap params = context.getMergedJobDataMap();

            EquipmentService equipmentService = (EquipmentService) params.get("equipmentService");
            int timeout = Integer.parseInt((String) params.get("timeout"));
            int port = Integer.parseInt((String) params.get("port"));

            TelnetConnectionFactory factory = TelnetConnectionFactory.getInstance();
            factory.setApPrefixes((String) params.get("apTypePrefixes"));
            factory.setGsPrefixes((String) params.get("gsTypePrefixes"));

            List<Equipment> equipments = equipmentService.loadEquipments();
            logger.info("Telnet equipment loaded {} entities", equipments.size());
            for (Equipment equipment : equipments) {
                logger.info("Telnet equipment[id={}, type={}, ip={}] checking start",
                        equipment.getId(), equipment.getType(), equipment.getIpAddress());

                TelnetConnection telnetConnection = factory.getTelnetConnection(equipment);
                String ip = equipment.getIpAddress();
                TelnetStatus telnetStatus = telnetConnection.connect(ip, port, timeout);

                Long id = equipment.getId();
                equipmentService.updateTelnetStatus(id, telnetStatus);
                logger.info("Telnet equipment[id={}, type={}, ip={}] checking ended, telnetStatus={}",
                        equipment.getId(), equipment.getType(), equipment.getIpAddress(), telnetStatus);
            }

            long ms = System.currentTimeMillis() - startTime;
            long hours = MILLISECONDS.toHours(ms);
            long minutes = MILLISECONDS.toMinutes(ms);
            long seconds = MILLISECONDS.toSeconds(ms);
            String duration = String.format("%02dh:%02dm:%02ds",
                    hours,
                    minutes - HOURS.toMinutes(hours),
                    seconds - MINUTES.toSeconds(minutes));

            logger.info("Telnet equipment status checking ended, execution time {}", duration);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
