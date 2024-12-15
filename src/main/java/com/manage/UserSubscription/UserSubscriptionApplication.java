package com.manage.UserSubscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling
public class UserSubscriptionApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserSubscriptionApplication.class, args);
	}
}
