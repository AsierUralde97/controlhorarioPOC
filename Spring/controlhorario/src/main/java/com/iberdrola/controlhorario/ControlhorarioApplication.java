package com.iberdrola.controlhorario;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.iberdrola.controlhorario.mappers")
public class ControlhorarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlhorarioApplication.class, args);
	}

}
