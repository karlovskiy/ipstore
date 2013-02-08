package utilits.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class BackupDatabaseJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(BackupDatabaseJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //todo
    }
}
