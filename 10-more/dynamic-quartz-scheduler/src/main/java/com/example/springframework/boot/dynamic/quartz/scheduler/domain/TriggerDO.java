package com.example.springframework.boot.dynamic.quartz.scheduler.domain;

import lombok.Data;
import org.quartz.*;

import java.text.ParseException;

/**
 * 触发器域
 */
@Data
public class TriggerDO {

    /**
     * trigger info
     */
    private String name;
    private String group;
    private String cronExpression;
    private String description;
    /**
     * 触发器状态：
     * STATE_BLOCKED 4 阻塞
     * STATE_ERROR 3 错误
     * STATE_COMPLETE 2 完成
     * STATE_PAUSED 1 暂停
     * STATE_NORMAL 0 正常
     * STATE_NONE -1 不存在
     */
    private Trigger.TriggerState triggerState;

    public CronTrigger convert2QuartzTrigger(JobDetail jobDetail) {
        CronExpression ce = null;
        try {
            if (cronExpression == null || cronExpression.isEmpty()) {
                throw new NullPointerException("cronExpression参数非法");
            }
            ce = new CronExpression(this.cronExpression);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert ce != null;
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule(ce))
                .withIdentity(this.name, this.group)
                .withDescription(this.description)
                .build();
    }
}
