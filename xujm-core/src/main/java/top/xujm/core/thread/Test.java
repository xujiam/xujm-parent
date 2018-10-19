package top.xujm.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Xujm
 */
@Component
public class Test {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async("taskExecutor")
    public void test(){
        logger.info("====start===");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("=====end====");
    }
}
