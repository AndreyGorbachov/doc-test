package by.agorbachev.authorizationservice.service;

import by.agorbachev.authorizationservice.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {

	Iterable<User> getAllUsers();

	User getById(Long userId);

	User createUser(User newUser);

	User updateUser(User newUser, Long userId);

	void deleteUserById(Long userId);

}
