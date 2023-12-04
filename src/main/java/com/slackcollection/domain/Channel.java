package com.slackcollection.domain;

public record Channel(
    ChannelId id,
    String name,
    boolean readable
) {

  public record ChannelId(String id)
  { }

}
