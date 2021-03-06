package com.hanson.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringCloudApplication
@EnableHystrixDashboard
@EnableFeignClients
public class FeignHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignHystrixApplication.class, args);
	}
}
