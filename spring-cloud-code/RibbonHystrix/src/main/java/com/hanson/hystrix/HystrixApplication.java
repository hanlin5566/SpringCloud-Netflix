package com.hanson.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//注解整合了多个注解，@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker等
@SpringCloudApplication
@RestController
@EnableHystrixDashboard
public class HystrixApplication {
	public static void main(String[] args) {
		SpringApplication.run(HystrixApplication.class, args);
	}
	
	@Autowired
    RestTemplate client;
	
	private long preTime = System.currentTimeMillis();
	
	@Value("${server.port}")
    private String port;
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	//熔断触发的callback方法，方法名为fireError
	@HystrixCommand(fallbackMethod = "fireError")
	@RequestMapping("/")
    public String helloWorld() {
		//hanson-service 为服务提供者的配置的spring.application.name
		String forObject = client.getForObject("http://hanson-service/", String.class);
		long nowTime = System.currentTimeMillis();
		try {
			if((nowTime - preTime) > 5 * 1000){
				//俩次请求间隔大于五秒，模拟一个异常，触发熔断
				throw new RuntimeException("call hanson service fail.");
			}
		} finally {
			preTime = nowTime;
		}
		forObject = String.format("port:%s  %s",port,forObject);
		System.out.println();
        return forObject;
    }
	//如果保护的方法有参数，此处可以传递与其一致的参数
	public String fireError() {
        return "some exception occur call fallback method.";
    }
}
