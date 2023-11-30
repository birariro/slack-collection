package com.slackcollection.adapter.in.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.slackcollection.application.ChannelService;
import com.slackcollection.domain.Channel;
import com.slackcollection.domain.ChannelMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "channel")
public class ChannelRouter {

  private final ChannelService channelService;

  @Operation(summary = "channel 전체 조회")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200")
  })
  @GetMapping("/channel")
  public ResponseEntity channel(){

    List<Channel> channels = channelService.find();
    ChannelResponse channelResponse = new ChannelResponse(channels);

    return ResponseEntity.ok().body(channelResponse);
  }

  @Operation(summary = "특정 channel 의 메시지 전체 조회")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200")
  })
  @GetMapping("/channel/{id}/message")
  public ResponseEntity message(@PathVariable("id") String id){

    List<ChannelMessage> channelMessage = channelService.findMessage(id);
    return ResponseEntity.ok().body(channelMessage);
  }
}
