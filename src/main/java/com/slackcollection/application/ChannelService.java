package com.slackcollection.application;

import java.util.List;
import org.springframework.stereotype.Service;
import com.slackcollection.domain.Channel;
import com.slackcollection.domain.Channel.ChannelId;
import com.slackcollection.domain.ChannelMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelService {

  private final SlackReader slackReader;

  public List<Channel> find(){

    return slackReader.findChannel();
  }

  public List<ChannelMessage> findMessage(String channelId){

    return slackReader.findMessageByChannel(new ChannelId(channelId));
  }

}
