package com.example.springframework.boot.dynamic.quartz.scheduler.domain;

import com.example.springframework.boot.dynamic.quartz.scheduler.common.SystemConstant;
import com.example.springframework.boot.dynamic.quartz.scheduler.job.HttpJob;
import com.example.springframework.boot.dynamic.quartz.scheduler.job.SimpleJob;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.core.jmx.JobDataMapSupport;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static lombok.Lombok.checkNotNull;

/**
 * 作业DO
 */
@Slf4j
@Data
public class JobDO {
    /**
     * 支持的任务类型
     */
    private static final Map<String, Class<? extends Job>> SUPPORTED_JOB_TYPES = new HashMap<>();

    static {
        SUPPORTED_JOB_TYPES.put(SystemConstant.JobType.SIMPLE_JOB, SimpleJob.class);
        SUPPORTED_JOB_TYPES.put(SystemConstant.JobType.HTTP_JOB, HttpJob.class);
    }

    /**
     * job info
     */
    private String name;
    private String group;
    private String targetClass;
    private String description;

    /**
     * ext info 拓展字段
     * 拓展字段包含:
     * JobType : SystemConstant.JobType
     * method : HttpMethod
     * url : http invoke url
     * jsonParams ; method params
     */
    private Map<String, Object> extInfo;

    public JobDetail convert2QuartzJobDetail() {
        Class<? extends Job> clazz = null;

        // 如果未定义 则根据extInfo里type获取默认处理类
        if (Objects.isNull(this.targetClass)) {
            String type = String.valueOf(this.extInfo.get("type"));
            clazz = SUPPORTED_JOB_TYPES.get(type);
            checkNotNull(clazz, "未找到匹配type的Job");
            this.targetClass = clazz.getCanonicalName();
        }
        try {
            clazz = (Class<Job>) ClassUtils.resolveClassName(this.targetClass, this.getClass().getClassLoader());
        } catch (IllegalArgumentException e) {
            log.error("加载类错误", e);
        }

        return JobBuilder.newJob()
                .ofType(clazz)
                .withIdentity(this.name, this.getGroup())
                .withDescription(this.description)
                .setJobData(JobDataMapSupport.newJobDataMap(this.extInfo))
                .build();
    }
}
