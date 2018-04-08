package com.hanson.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class SleuthServiceApplication {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(SleuthServiceApplication.class, args);
	}
	
	@Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    
    private String url="http://localhost:9001";
    
    @RequestMapping("/service")
    public String service1() throws Exception {
    	logger.info("service call service1");
        Thread.sleep(200L);
        String s = this.restTemplate.getForObject(url + "/service1", String.class);
        return s;
    }  
}
