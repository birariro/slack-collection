package com.slackcollection.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HelloController {


  @GetMapping("/hello")
  public ResponseEntity hello(){
    return ResponseEntity.ok().body("world");
  }

}
