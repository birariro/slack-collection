package com.slackcollection.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

  @Scheduled(cron="${setting.schedule.cron:0 0 8 * * ?}", zone="Asia/Seoul")
  public void execute(){
      log.info("똑딱");
  }
}
