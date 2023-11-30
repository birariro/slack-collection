package com.slackcollection.domain;

public record ChannelMessage(
    String channelId,
    String author,
    String text,
    String ts)
{

}
