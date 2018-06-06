package com.example.springframework.boot.dynamic.quartz.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class HttpJob extends QuartzJobBean {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    private OkHttpClient okHttpClient;

    /**
     * 构造request
     *
     * @param url        访问链接
     * @param jsonParams json请求参数
     * @return 请求
     */
    public static Request buildRequest(String method, String url, String jsonParams) {
        Request.Builder builder = new Request.Builder();
        Request request;
        if (Objects.equals(method, HttpMethod.GET.toString())) {
            request = builder.url(url)
                    .get()
                    .build();
        } else {
            RequestBody body = RequestBody.create(JSON, jsonParams);
            request = builder.url(url)
                    .post(body)
                    .build();
        }
        return request;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        TriggerKey triggerKey = context.getTrigger().getKey();
        String jobGroup = jobKey.getGroup();
        String jobName = jobKey.getName();
        String triggerGroup = triggerKey.getGroup();
        String triggerName = triggerKey.getName();
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        //http
        String url = String.valueOf(jobDataMap.get("url"));
        String method = String.valueOf(jobDataMap.get("method"));
        String jsonStr = String.valueOf(jobDataMap.get("jsonParams"));
        Request request = buildRequest(method, url, jsonStr);
        Response response = null;
        String result = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (Objects.nonNull(response)) {
                result = response.body() != null ? response.body().string() : null;
            }
        } catch (Exception e) {
            log.error("http调用出错", e);
        } finally {
            log.info("[ TIME:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            + " , JOB_GROUP:" + jobGroup
                            + " , JOB_NAME:" + jobName
                            + " , TRIGGER_GROUP:" + triggerGroup
                            + " , TRIGGER_NAME:" + triggerName
                            + " ] method:{} | url:{} | params:{} | response: {}",
                    method,
                    url,
                    jsonStr,
                    result
            );
            if (Objects.nonNull(response)) {
                response.close();
            }
        }
    }
}
