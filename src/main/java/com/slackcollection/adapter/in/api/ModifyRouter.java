package com.slackcollection.adapter.in.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public class ModifyRouter {

  @Operation(summary = "channel 을 동기화 한다")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200")
  })
  @PutMapping("/modify/channel")
  public ResponseEntity modifyChannel(){

    return ResponseEntity.ok().build();
  }

  @Operation(summary = "특정 channel 의 메시지를 동기화한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200")
  })
  @GetMapping("/modify/channel/{id}/message")
  public ResponseEntity modifyMessage(@PathVariable("id") String id){

    return ResponseEntity.ok().build();
  }

}
