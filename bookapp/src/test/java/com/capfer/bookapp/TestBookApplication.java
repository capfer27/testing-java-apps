package com.capfer.bookapp;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.SpringApplication;

@Disabled
public class TestBookApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
