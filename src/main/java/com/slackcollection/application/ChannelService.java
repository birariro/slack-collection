package com.slackcollection.application;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Message;
import com.slackcollection.domain.Channel;
import com.slackcollection.domain.ChannelMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelService {

  @Value("${slack.token}")
  private String token;

  public List<Channel> find(){

    var client = Slack.getInstance().methods();

    try {
        var result = client.conversationsList(r -> r.token(token));

        List<Channel> channels = result.getChannels().stream()
            .map(channel -> new Channel(channel.getId(), channel.getName(), channel.isMember()))
            .toList();

        return channels;

      } catch (IOException | SlackApiException e) {
        log.warn("ChannelService Exception: {}", e.getMessage());
      }
    return new ArrayList<>();
  }

  public List<ChannelMessage> findMessage(String channelId){

    var client = Slack.getInstance().methods();
    try {
        var result = client.conversationsHistory(r -> r
            .token(token)
            .channel(channelId)
        );

        List<Message> messages = Optional.ofNullable(result.getMessages()).orElseGet(() -> new ArrayList<>());
        List<ChannelMessage> channelMessages = messages.stream()
            .map(message -> new ChannelMessage(channelId, message.getTs(), message.getUsername(), message.getText(), unixTsToLocalDateTime(
                message.getTs())))
            .toList();

        log.info("channel id '{}' in message count {}", channelId, channelMessages.size());

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
