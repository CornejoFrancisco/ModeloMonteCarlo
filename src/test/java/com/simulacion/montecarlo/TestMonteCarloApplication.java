package com.simulacion.montecarlo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration(proxyBeanMethods = false)
public class TestMonteCarloApplication {


	public static void main(String[] args) {
		SpringApplication.from(MonteCarloApplication::main).with(TestMonteCarloApplication.class).run(args);
	}

}
