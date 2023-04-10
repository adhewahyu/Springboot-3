package com.dan.taskservice.controller;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final WebClient webClientBasic;

    @GetMapping("/v1/get")
    public Mono<ResponseEntity<String>> getString(){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is bad request");
    }

    @PostMapping("/v1/try")
    public void doTry(){
        webClientBasic.get()
                .uri("http://localhost:81/test/v1/get")
                .retrieve()
                .onStatus(httpStatusCode -> (httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError()),
                        clientResponse -> clientResponse.bodyToMono(Map.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(clientResponse.statusCode(), error.get("message").toString()))))
                .bodyToMono(String.class)
                .subscribe(data -> log.info("response = {}", JSON.toJSONString(data)));
    }

}
