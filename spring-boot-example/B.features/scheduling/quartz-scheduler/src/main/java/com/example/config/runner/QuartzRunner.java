package com.example.config.runner;

import com.example.config.quartz.tool.QuartzHelper;
import com.example.config.quartz.job.HelloQuartzJob;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** 服务启动，注册quartz任务调度 */
@Component
public class QuartzRunner implements ApplicationRunner {
  private final QuartzHelper quartzHelper;

  public QuartzRunner(QuartzHelper quartzHelper) {
    this.quartzHelper = quartzHelper;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    quartzHelper.removeJob("helloQuartzJob");
    quartzHelper.addCronJob(HelloQuartzJob.class, "helloQuartzJob", "0/5 * * * * ?");
  }
}
