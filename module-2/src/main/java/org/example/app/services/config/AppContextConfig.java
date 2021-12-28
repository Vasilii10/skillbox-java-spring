package org.example.app.services.config;

import org.example.app.services.IdProvider;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "org.example.app")
public class AppContextConfig {

	@Bean
	public IdProvider idProvider(){
		return new IdProvider();
	}
}
