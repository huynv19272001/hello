package com.lpb.service.viettel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ViettelMain {
    public static void main(String[] args) {
        Environment env = SpringApplication.run(ViettelMain.class, args).getEnvironment();
    }
}
