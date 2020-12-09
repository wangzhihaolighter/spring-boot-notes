package com.example.dynamic.scheduling.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "schedule_job_log")
public class ScheduleJobLog {
    private long id;
    private Timestamp createTime;
    private long jobId;
    private String jobName;
    private String jobMemo;
    private String jobCronExpression;
    private byte jobType;
    private String jobExt;
    private long spendTime;
    private boolean success;
    private String exceptionDetail;

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
    @Column(name = "job_id")
    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
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

    @Basic
    @Column(name = "spend_time")
    public long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
    }

    @Basic
    @Column(name = "success")
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Basic
    @Column(name = "exception_detail")
    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleJobLog that = (ScheduleJobLog) o;
        return id == that.id &&
                jobId == that.jobId &&
                jobType == that.jobType &&
                spendTime == that.spendTime &&
                success == that.success &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(jobName, that.jobName) &&
                Objects.equals(jobMemo, that.jobMemo) &&
                Objects.equals(jobCronExpression, that.jobCronExpression) &&
                Objects.equals(jobExt, that.jobExt) &&
                Objects.equals(exceptionDetail, that.exceptionDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime, jobId, jobName, jobMemo, jobCronExpression, jobType, jobExt, spendTime, success, exceptionDetail);
    }
}
