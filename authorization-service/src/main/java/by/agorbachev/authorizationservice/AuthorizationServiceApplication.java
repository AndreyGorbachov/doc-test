package by.agorbachev.authorizationservice;

import by.agorbachev.authorizationservice.dao.UsersRepository;
import by.agorbachev.authorizationservice.model.Role;
import by.agorbachev.authorizationservice.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class AuthorizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServiceApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner dataLoader(UsersRepository usersRepo, PasswordEncoder passwordEncoder) {
		return args -> {
			Optional<User> userOpt = usersRepo.findByEmail("admin");
			if (userOpt.isEmpty()) {
				String email = "admin";
				String password = passwordEncoder.encode("admin");
				User adminUser = new User(null, email, password, Role.ADMIN);
				usersRepo.save(adminUser);
			}
		};
	}

}
