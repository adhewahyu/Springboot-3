package com.dan.taskservice.adaptor.user;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.taskservice.model.request.CreateRoleRequest;
import com.dan.taskservice.model.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateRoleByTaskAdaptor {

    @Value("${integration.user.createRoleByTaskUrl}")
    private String url;

    @Value("${integration.user.apiKey}")
    private String internalApiKey;

    private final WebClient webClientBasic;

    public void execute(CreateRoleRequest createRoleRequest){
        log.info("url = {}", url);
        webClientBasic
                .method(HttpMethod.POST)
                .uri(url)
                .headers(this::getHttpHeaders)
                .body(Mono.just(createRoleRequest), CreateRoleRequest.class)
                .retrieve()
                .onStatus(httpStatusCode -> (httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError()),
                        clientResponse -> clientResponse.bodyToMono(Map.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(clientResponse.statusCode(), error.get("message").toString()))))
                .bodyToMono(RestResponse.class)
                .subscribe(data -> log.info("response = {}", JSON.toJSONString(data)));
    }

    private void getHttpHeaders(HttpHeaders httpHeaders) {
        httpHeaders.add(CommonConstants.REQ_HEADER_APIKEY, internalApiKey);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }

}
