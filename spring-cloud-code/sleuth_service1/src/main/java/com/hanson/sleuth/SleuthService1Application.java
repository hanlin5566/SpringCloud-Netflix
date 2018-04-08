package com.hanson.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SleuthService1Application {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(SleuthService1Application.class, args);
	}
	
    @RequestMapping("/service1")
    public String service1() throws Exception {
    	logger.info("service1 invoker");
        Thread.sleep(200L);
        return "service1";
    }  
}
