package com.slackcollection.config;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

  private final JobLauncher jobLauncher;
  private final Job combineJob;

  @Scheduled(cron="${setting.schedule.cron:0 0 8 * * ?}", zone="Asia/Seoul")
  public void execute()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

    log.info("batch run");

    jobLauncher.run(combineJob, new JobParametersBuilder()
        .addString("datetime", LocalDateTime.now().toString())
        .toJobParameters());
  }

  @Scheduled(cron="0 0 8 * * ?", zone="Asia/Seoul")
  public void timer() {
    log.info("똑딱");
  }
}
