package com.sky.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 自定义定时任务
 */
@Component
@Slf4j
public class MyTask {
    /**
     * 定时任务，每隔5秒，触发一次
     */
    @Scheduled(cron = "0/5 * * * * ?")//秒 分 时 日 月 周（每5秒执行一次）
    public void executeTask(){
//        log.info("定时任务开始执行：{}",new Date());
    }

}
