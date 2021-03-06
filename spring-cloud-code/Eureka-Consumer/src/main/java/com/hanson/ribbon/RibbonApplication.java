package com.hanson.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class RibbonApplication {
	/**
	 * 注入声明的
	 */
	@Autowired
    RestTemplate client;


	public static void main(String[] args) {
		SpringApplication.run(RibbonApplication.class, args);
	}
	/**
	 * 声明一个RestTemplate的bean。
	 * 通过@LoadBalanced注解,表明这个restRemplate开启负载均衡的功能。
	 * @return
	 */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@RequestMapping("/")
    public String helloWorld() {
		//hanson-service 为服务提供者的配置的spring.application.name
		String forObject = client.getForObject("http://hanson-service/", String.class);
		System.out.println(forObject);
        return forObject;
    }
}
