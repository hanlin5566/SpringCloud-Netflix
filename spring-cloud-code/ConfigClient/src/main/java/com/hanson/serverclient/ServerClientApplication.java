package com.hanson.serverclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
//使用该注解后，可以在运行时直接刷新Bean，并在下次方法调用时，得到一个全新的实例。　　
@RefreshScope //注解@RefreshScope指示Config客户端在服务器配置改变时，也刷新注入的属性值
public class ServerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerClientApplication.class, args);
	}

	@Value("${init.failed.exit}") // 读取gitlab配置文件中的属性，如果我们读取到了值，说明客户端是OK的
	private String exited;

	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String hello() {
		return exited;
	}
}
