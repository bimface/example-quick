package com.bimface.example.quick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableScheduling
@MapperScan(basePackages = "com.bimface.example.quick.dao.mapper")
@SpringBootApplication
public class ExampleQuickApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExampleQuickApplication.class, args);
	}
}
