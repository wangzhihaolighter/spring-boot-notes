package com.example.dynamic.scheduling.util;

import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * cron表达式工具类
 */
public class CronUtils {

    /**
     * 按时间计算下次执行时间
     */
    public static Date getExecuteTime(String cron, Date date) {
        if (StringUtils.isEmpty(cron)) {
            throw new IllegalArgumentException("cron表达式不可为空");
        }
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        return cronSequenceGenerator.next(date);
    }

    /**
     * 根据当前计算下次执行时间
     */
    public static Date getExecuteTimeByNow(String cron) {
        return getExecuteTime(cron, new Date());
    }

    /**
     * 计算多个执行时间
     *
     * @param cron  表达式
     * @param count 执行时间个数
     * @return /
     */
    public static List<Date> getExecuteTimes(String cron, Integer count) {
        if (StringUtils.isEmpty(cron)) {
            throw new IllegalArgumentException("cron表达式不可为空");
        }
        count = count == null || count < 1 ? 1 : count;
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        List<Date> list = new ArrayList<>(count);
        Date nextTimePoint = new Date();
        for (int i = 0; i < count; i++) {
            // 计算下次时间点的开始时间
            nextTimePoint = cronSequenceGenerator.next(nextTimePoint);
            list.add(nextTimePoint);
        }
        return list;
    }

}
