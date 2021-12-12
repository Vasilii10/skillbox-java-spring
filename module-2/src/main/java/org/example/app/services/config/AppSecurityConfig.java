package org.example.app.services.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	private final Logger log = Logger.getLogger(AppSecurityConfig.class);

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
			.withUser("vasilii")
			.password(passwordEncoder().encode("123"))
			.roles("USER");
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(10);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/* Something to database */
		http.headers().frameOptions().disable();

		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login*").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login")
			.loginProcessingUrl("/login/auth")
			.defaultSuccessUrl("/books/shelf", true)
			.failureUrl("/login");
	}

	/* Requesting static resources accepted */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring().antMatchers("/images/**");
	}
}
