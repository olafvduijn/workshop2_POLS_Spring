package view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import domein.Account;
import domein.Adres;
import domein.Klant;



@Configuration
@ComponentScan
public class SpringConfiguration {

	public SpringConfiguration() {
	}
	

	@Bean
	public Klant klant() {
		return new Klant();
	}
	@Bean
	public Account account() {
		return new Account();
	}
	
	@Bean
	public Adres adres() {
		return new Adres();
	}
	

}
