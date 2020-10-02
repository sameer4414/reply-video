package com.org.reply.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * NOTE if not exclusing was getting error when starting the application
 * Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = "com.org.reply.payment")
public class ReplyVideoPaymentApplication {

	@Value("${server.port}")
	String serverPort;

	public static void main(String[] args) {
		SpringApplication.run(ReplyVideoPaymentApplication.class, args);
	}

}
