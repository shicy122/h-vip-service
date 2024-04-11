package com.hycan.idn.hvip;

import com.hycan.idn.tsp.common.feign.annotation.EnableTspFeignClients;
import com.hycan.idn.tsp.common.job.annotation.EnableTspXxlJob;
import com.hycan.idn.tsp.common.security.annotation.EnableTspResourceServer;
import com.hycan.idn.tsp.common.swagger.annotation.EnableTspSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author pig archetype
 * <p>
 * 项目启动类
 */
@EnableKafka
@EnableTspSwagger2
@EnableTspFeignClients
@EnableTspResourceServer
@EnableDiscoveryClient
@EnableScheduling
@EnableTspXxlJob
@EnableJpaRepositories(basePackages = "com.hycan.idn.hvip.repository")
@SpringBootApplication
public class HVipServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HVipServiceApplication.class, args);
    }

}

