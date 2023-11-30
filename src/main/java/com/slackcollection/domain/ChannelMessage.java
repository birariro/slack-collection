package com.slackcollection.domain;

import java.time.LocalDateTime;

public record ChannelMessage(
    String channelId,
    String id, //message id 로 사용할 만한 값이 timestamp 이다
    String author,
    String text,
    LocalDateTime messagedAt)
{

}
