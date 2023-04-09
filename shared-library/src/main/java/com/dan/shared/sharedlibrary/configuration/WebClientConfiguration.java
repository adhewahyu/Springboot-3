package com.dan.shared.sharedlibrary.configuration;

import com.dan.shared.sharedlibrary.util.CommonConstants;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class WebClientConfiguration {

    @Value("${config.webclient.connectTimeoutMillis:5000}")
    private Integer connectTimeoutMillis;

    @Value("${config.webclient.responseTimeoutMillis:5000}")
    private Integer responseTimeoutMillis;

    @Value("${config.webclient.readTimeoutMillis:5000}")
    private Integer readTimeoutMillis;

    @Value("${config.webclient.writeTimeoutMillis:5000}")
    private Integer writeTimeoutMillis;

    @Value("${config.webclient.sslBypass:false}")
    private Boolean sslBypass;

    @Bean(name = "webClientBasic")
    public WebClient getCommonHttpClient(){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis)
                .responseTimeout(Duration.ofMillis(responseTimeoutMillis))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(readTimeoutMillis, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(writeTimeoutMillis, TimeUnit.MILLISECONDS)));
        if(sslBypass){
            httpClient.secure(t -> t.sslContext(getSslContext()));
        }
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    private SslContext getSslContext(){
        try {
            return SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } catch (SSLException e) {
            log.error("SSL Context Exception = {}", e.getMessage());
            log.error(ExceptionUtils.getStackTrace(e));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstants.ERR_MSG_SOMETHING_WRONG);
        }
    }
}
