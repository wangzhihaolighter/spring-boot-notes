package com.example.dynamic.scheduling.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "schedule_job")
public class ScheduleJob {
    private long id;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private String jobName;
    private String jobMemo;
    private String jobCronExpression;
    private boolean enable;
    private boolean repeatable;
    private byte jobType;
    private String jobExt;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "last_update_time")
    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Basic
    @Column(name = "job_name")
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Basic
    @Column(name = "job_memo")
    public String getJobMemo() {
        return jobMemo;
    }

    public void setJobMemo(String jobMemo) {
        this.jobMemo = jobMemo;
    }

    @Basic
    @Column(name = "job_cron_expression")
    public String getJobCronExpression() {
        return jobCronExpression;
    }

    public void setJobCronExpression(String jobCronExpression) {
        this.jobCronExpression = jobCronExpression;
    }

    @Basic
    @Column(name = "enable")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "repeatable")
    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    @Basic
    @Column(name = "job_type")
    public byte getJobType() {
        return jobType;
    }

    public void setJobType(byte jobType) {
        this.jobType = jobType;
    }

    @Basic
    @Column(name = "job_ext")
    public String getJobExt() {
        return jobExt;
    }

    public void setJobExt(String jobExt) {
        this.jobExt = jobExt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleJob that = (ScheduleJob) o;
        return id == that.id &&
                enable == that.enable &&
                repeatable == that.repeatable &&
                jobType == that.jobType &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(lastUpdateTime, that.lastUpdateTime) &&
                Objects.equals(jobName, that.jobName) &&
                Objects.equals(jobMemo, that.jobMemo) &&
                Objects.equals(jobCronExpression, that.jobCronExpression) &&
                Objects.equals(jobExt, that.jobExt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime, lastUpdateTime, jobName, jobMemo, jobCronExpression, enable, repeatable, jobType, jobExt);
    }
}
