package by.agorbachev.authorizationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static by.agorbachev.authorizationservice.rest.ApiController.BASE_URL;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(requestMatcherRegistry ->
						requestMatcherRegistry
								.requestMatchers(BASE_URL + "/admin/**").hasRole("ADMIN")
								.requestMatchers(BASE_URL, BASE_URL + "/*").hasAnyRole("ADMIN", "USER")
								.anyRequest()
								.authenticated()
				)
				.formLogin(Customizer.withDefaults());
		return http.build();
	}

}
