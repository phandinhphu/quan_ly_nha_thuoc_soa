package com.pma.reporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // Quan trọng để các Interface Client hoạt động
public class ReporterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReporterServiceApplication.class, args);
    }
}