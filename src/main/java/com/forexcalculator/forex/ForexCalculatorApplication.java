package com.forexcalculator.forex;

import com.forexcalculator.forex.config.LoggerSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForexCalculatorApplication  extends LoggerSingleton {

	public static void main(String[] args) {
		SpringApplication.run(ForexCalculatorApplication.class, args);
	}

}
