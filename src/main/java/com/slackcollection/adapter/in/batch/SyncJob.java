package com.slackcollection.adapter.in.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SyncJob {

  private final SyncChannelStepConfig channelStep;
  private final SyncMessageStepConfig syncMessageStep;

  @Bean
  public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new JobBuilder("syncJob",jobRepository)
        .start(channelStep.syncChannelStep(jobRepository,transactionManager))
        .next(syncMessageStep.syncMessageStep(jobRepository,transactionManager))
        .build();
  }
}
