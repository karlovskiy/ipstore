package utilits.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class BackupDatabaseJob extends QuartzJobBean {

    private static final Logger logger = Logger.getLogger(BackupDatabaseJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("starting backup database job...");
    }
}
