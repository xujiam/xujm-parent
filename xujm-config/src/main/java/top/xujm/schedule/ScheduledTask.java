package top.xujm.schedule;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.config.model.PlatformScheduled;
import top.xujm.modules.config.repository.PlatformScheduledRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Xujm
 */
@Component
public class ScheduledTask{

    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired(required = false)
    private Map<String,ScheduledService> scheduledServiceMap;
    @Autowired
    private PlatformScheduledRepository platformScheduledRepository;

    public static Map<String, PlatformScheduled> scheduledMap = new HashMap<>();

    public void startCron() {
        if(scheduledServiceMap == null){
            return;
        }
        String curIp = LibSysUtil.getCurServiceIp();
        List<PlatformScheduled> list = platformScheduledRepository.findAll();
        logger.info("startCronIp: {}",curIp);
        for (PlatformScheduled info:list){
            ScheduledService scheduledService = getScheduledService(info.getScheduledCode());
            if(scheduledService == null){
                logger.info("{} start error : scheduledService not found",info.getScheduledName());
                break;
            }
            if(!StringUtils.equals(info.getRunIp(),curIp)){
                logger.info("{} start error : runIp is {},serviceIp:{}",info.getScheduledName(),info.getRunIp(),curIp);
                break;
            }
            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(scheduledService, new CronTrigger(info.getScheduledCron()));
            info.setFuture(future);
            scheduledMap.put(info.getScheduledCode(),info);
            logger.info("{} start success",info.getScheduledName());
        }
    }

    public void startCron(String scheduledCode, String cron) {
        PlatformScheduled platformScheduled = scheduledMap.get(scheduledCode);
        ScheduledService scheduledService = getScheduledService(scheduledCode);
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(scheduledService, new CronTrigger(cron));
        platformScheduled.setFuture(future);
        platformScheduled.setScheduledCron(cron);
        scheduledMap.put(platformScheduled.getScheduledCode(),platformScheduled);
        logger.info("{} start success",platformScheduled.getScheduledName());
    }

    public void stopCron(String scheduledCode) {
        PlatformScheduled platformScheduled = scheduledMap.get(scheduledCode);
        if(platformScheduled != null){
            platformScheduled.getFuture().cancel(true);
        }
        System.out.println("DynamicTask.scheduledCode.stopCron()");
    }

    public void restartCron(String scheduledCode,String cron){
        stopCron(scheduledCode);
        startCron(scheduledCode,cron);
        System.out.println("DynamicTask.scheduledCode.restartCron()");
    }

    private ScheduledService getScheduledService(String scheduledCode){
        return scheduledServiceMap.get(String.format("%sScheduledServiceImpl",scheduledCode));
    }

    public List<PlatformScheduled> getCronList(){
        List<PlatformScheduled> list = new ArrayList<>();
        for (Map.Entry<String,PlatformScheduled> entry:scheduledMap.entrySet()){
            PlatformScheduled platformScheduled = entry.getValue();
            platformScheduled.setScheduledState((byte)(platformScheduled.getFuture().isDone()?0:1));
            list.add(platformScheduled);
        }
        return list;
    }
}
