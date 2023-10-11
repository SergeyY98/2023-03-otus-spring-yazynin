package ru.otus.spring;

import ru.otus.spring.services.MetamorphosisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		MetamorphosisService orderService = ctx.getBean(MetamorphosisService.class);
		orderService.process();
	}

}
