package com.tenerdy.mobilegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.EnableWebFlux;

//@EnableWebFlux
@SpringBootApplication
@EnableDiscoveryClient
public class MobileGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileGatewayApplication.class, args);
    }

}
