package br.com.financeiro.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceiroApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApiApplication.class, args);
	}

	/*@Bean
	public WebMvcConfigurer configurarCors() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("http://localhost:8000");
			}
		};
	}*/

}
