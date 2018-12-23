package com.jenna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jenna.security.AppProperties;

@SpringBootApplication //server가 러닝될때 자동적으로 시작되는 기준점(root)
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@Bean
   public BCryptPasswordEncoder bCryptPasswordEncoder() { //password encorder ,대중적으로 쓸수 있도록 main에 넣어놓는다.
      return new BCryptPasswordEncoder();
   }	
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
	@Bean(name="AppProperties")
   public AppProperties getAppProperties() {
      return new AppProperties();
   }
}
