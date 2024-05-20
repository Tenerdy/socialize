package com.tenerdy.mobileuserinfo;

import com.tenerdy.mobiledomain.entity.UserTags;
import com.tenerdy.mobileuserinfo.mapper.UserTagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tenerdy.mobileuserinfo.feign"})
public class MobileUserInfoApplication {



    public static void main(String[] args) {
        SpringApplication.run(MobileUserInfoApplication.class, args);
    }



}
