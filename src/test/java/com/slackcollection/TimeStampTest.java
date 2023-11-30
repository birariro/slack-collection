package com.slackcollection;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class TimeStampTest {

  @Test
  public void test(){

    String s = "1701302409.598619";
    double timestamp = 1701302409.598619;

    // Unix 타임스탬프를 초 단위로 변환
    Instant instant = Instant.ofEpochSecond((long) timestamp);

    // UTC 기준으로 Instant를 LocalDateTime으로 변환
    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

    // 로컬 시간대로 변환
    LocalDateTime localDateTimeInLocalZone = localDateTime.atZone(ZoneId.systemDefault()).toLocalDateTime();

    System.out.println(localDateTimeInLocalZone);

  }

}
