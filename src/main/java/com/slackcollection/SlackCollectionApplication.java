package com.slackcollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SlackCollectionApplication {

  public static void main(String[] args) {
    SpringApplication.run(SlackCollectionApplication.class, args);
  }

}
