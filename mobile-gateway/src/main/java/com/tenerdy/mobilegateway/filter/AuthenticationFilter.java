package com.tenerdy.mobilegateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.data.redis.core.StringRedisTemplate;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
@CrossOrigin("*")
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    public StringRedisTemplate stringRedisTemplate;
    public static final List<String> WHITE_URL = Arrays.asList("/mobile-authentication/register",
            "/mobile-authentication/login","/mobile-authentication/checkRegister"
            ,"/mobile-authentication/getTagsInfo");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //白名单放行
        if (WHITE_URL.contains(path)) {
            return chain.filter(exchange);
        }
        HttpHeaders headers = request.getHeaders();
        List<String> authorization = headers.get("Authorization");
        if (!CollectionUtils.isEmpty(authorization)) {
            String token = authorization.get(0);
            if (StringUtils.hasText(token)) {
                // 约定好的有前缀的 bearer token
                String realToken = token.replaceFirst("bearer ", "");
                if (StringUtils.hasText(realToken) && Boolean.TRUE.equals(stringRedisTemplate.hasKey(realToken))) {
                    return chain.filter(exchange);
                }
            }
        }
        // 拦截
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("content-type", "application/json;charset=utf-8");
        HashMap<String, Object> map = new HashMap<>(4);
        // 返回401
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        map.put("msg", "未授权");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
