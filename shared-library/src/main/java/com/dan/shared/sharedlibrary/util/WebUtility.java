package com.dan.shared.sharedlibrary.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
@Slf4j
public class WebUtility {

    public void doWriteCookie(ServerWebExchange exchange,
                              String cookieName,
                              String cookieValue,
                              Integer expiry,
                              Boolean secure,
                              Boolean httpOnly){
        ResponseCookie responseCookie = ResponseCookie.from(cookieName, cookieValue).build();
        responseCookie.mutate()
                .maxAge(expiry)
                .secure(secure)
                .httpOnly(httpOnly);
        exchange.getResponse().addCookie(responseCookie);
    }

    public void doWriteCookie(ServerWebExchange exchange,
                              String cookieName,
                              String cookieValue,
                              Integer expiry,
                              Boolean secure,
                              Boolean httpOnly,
                              Boolean sameSiteNone) {
        ResponseCookie responseCookie = ResponseCookie.from(cookieName, cookieValue).build();
        responseCookie.mutate()
                .maxAge(expiry)
                .secure(secure)
                .httpOnly(httpOnly);
        if(sameSiteNone){
            responseCookie.mutate().sameSite("None");
        }
        exchange.getResponse().addCookie(responseCookie);
    }

    public void doDeleteCookie(ServerWebExchange exchange, Boolean secure, Boolean httpOnly, String cookieName){
        ResponseCookie responseCookie = ResponseCookie.from(cookieName, null).build();
        responseCookie.mutate()
                .maxAge(0)
                .path("/")
                .secure(secure)
                .httpOnly(httpOnly);
        exchange.getResponse().addCookie(responseCookie);
    }

    public String getCookie(ServerWebExchange exchange,  String cookieName){
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst(cookieName);
        if(ObjectUtils.isNotEmpty(cookie)){
            return cookie.getValue();
        }
        return null;
    }
    
}
