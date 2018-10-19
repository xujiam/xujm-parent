package top.xujm.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Xujm
 */
@Component
public class ScheduledApplicationRunner implements ApplicationRunner {

    @Autowired
    private ScheduledTask scheduledTask;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scheduledTask.startCron();
    }

}
