package com.slackcollection.adapter.in.api;

import java.util.ArrayList;
import java.util.List;

import com.slackcollection.domain.Channel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelResponse {

  List<simpleChannel> readable = new ArrayList<>();
  List<simpleChannel> non_readable = new ArrayList<>();

  public ChannelResponse(List<Channel> channels) {
    for (Channel channel : channels) {
      simpleChannel simpleChannel = new simpleChannel(channel.id(), channel.name());
      if(channel.readable()) readable.add(simpleChannel);
      else non_readable.add(simpleChannel);
    }
  }

  public record simpleChannel(
      String id,
      String name
  ) { }
}
