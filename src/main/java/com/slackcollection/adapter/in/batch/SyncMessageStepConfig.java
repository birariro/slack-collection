package com.slackcollection.adapter.in.batch;

import java.util.ArrayList;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SyncMessageStepConfig {

  private final int chunkSize = 100;
  @Bean
  public Step syncMessageStep(
      JobRepository jobRepository, PlatformTransactionManager transactionManager)  {
    return new StepBuilder("syncMessageStep",jobRepository)
        .<Object, Object> chunk(chunkSize,transactionManager)
        .reader(syncMessageReader())
        .processor(syncMessageProcessor())
        .writer(syncMessageWriter())
        .build();
  }

  @Bean
  @StepScope
  public ListItemReader<Object> syncMessageReader() {

    ArrayList<Object> objects = new ArrayList<>();
    return new ListItemReader<>(objects);
  }


  public ItemProcessor<Object, Object> syncMessageProcessor(){
    return item ->{
      return item;
    };
  }

  public ItemWriter<Object> syncMessageWriter(){
    return items -> {};

  }
}