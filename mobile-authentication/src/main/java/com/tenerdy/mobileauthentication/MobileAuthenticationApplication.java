package com.tenerdy.mobileauthentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MobileAuthenticationApplication {



    public static void main(String[] args) {
        SpringApplication.run(MobileAuthenticationApplication.class, args);
    }

}
