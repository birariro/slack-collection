package com.slackcollection.application;

import java.util.List;


import com.slackcollection.domain.Channel;
import com.slackcollection.domain.Channel.ChannelId;
import com.slackcollection.domain.ChannelMessage;

public interface SlackReader {
  List<Channel> findChannel();
  List<ChannelMessage> findMessageByChannel(ChannelId id);
}
