package com.github.since1986.learn.cloud.service.a;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@EnableScheduling
@SpringBootApplication
public class App {

    private final EurekaClient eurekaClient;

    @Autowired
    public App(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Scheduled(initialDelay = 1000 * 10, fixedRate = 1000 * 30)
    public void printInstanceInfo() {
        List<InstanceInfo> instanceInfos = eurekaClient.getInstancesByVipAddress("com-github-since1986-learn-cloud-service-b", false);
        instanceInfos.forEach(instanceInfo -> {
            System.out.printf("发现了 %s: %s \n", instanceInfo.getAppName(), instanceInfo.getIPAddr());
        });
        System.out.println();
    }
}
