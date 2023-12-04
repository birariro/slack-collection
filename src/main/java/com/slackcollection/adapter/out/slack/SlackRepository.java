package com.slackcollection.adapter.out.slack;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slackcollection.application.SlackReader;
import com.slackcollection.domain.Channel;
import com.slackcollection.domain.Channel.ChannelId;
import com.slackcollection.domain.ChannelMessage;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@Transactional
public class SlackRepository implements SlackReader {

  @Value("${slack.token}")
  private String token;

  @Override
  public List<Channel> findChannel() {
    var client = Slack.getInstance().methods();

    try {
      var result = client.conversationsList(r -> r.token(token));

      List<Channel> channels = result.getChannels().stream()
          .map(channel -> new Channel(new ChannelId(channel.getId()), channel.getName(), channel.isMember()))
          .toList();

      return channels;

    } catch (IOException | SlackApiException e) {
      log.warn("ChannelService Exception: {}", e.getMessage());
    }
    return new ArrayList<>();
  }

  @Override
  public List<ChannelMessage> findMessageByChannel(ChannelId id) {
    var client = Slack.getInstance().methods();
    try {
      var result = client.conversationsHistory(r -> r
          .token(token)
          .channel(id.id())
      );

      List<com.slack.api.model.Message> messages = Optional.ofNullable(result.getMessages()).orElseGet(() -> new ArrayList<>());
      messages.stream().forEach(item ->
          log.debug(item.toString()));


      List<ChannelMessage> channelMessages = messages.stream()
          .map(message -> new ChannelMessage(id.id(), message.getTs(), message.getUsername(), message.getText(), unixTsToLocalDateTime(
              message.getTs())))
          .toList();

      log.info("channel id '{}' in message count {}", id.id(), channelMessages.size());

      return channelMessages;

    } catch (IOException | SlackApiException e) {
      log.warn("ChannelService Exception: {}", e.getMessage());
    }
    return new ArrayList<>();
  }

  private LocalDateTime unixTsToLocalDateTime(String ts){

    double timestamp = Double.parseDouble(ts);
    Instant instant = Instant.ofEpochSecond((long) timestamp);
    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    LocalDateTime localDateTimeInLocalZone = localDateTime.atZone(ZoneId.systemDefault()).toLocalDateTime();

    return localDateTimeInLocalZone;

  }
}
