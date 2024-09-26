package com.colak.springtutorial.controller;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")

@Slf4j
public class HelloWorldController {


    @GetMapping
    @TimeLimiter(name = "timeLimiterExample" , fallbackMethod = "fallback")
    public ResponseEntity<String> showHelloWorld(){
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    public ResponseEntity<String> fallback(Throwable e){
        log.error("fallback exception , {}",e.getMessage());
        return new ResponseEntity<>("your request is too fast,please low down", HttpStatus.OK);
    }

}
