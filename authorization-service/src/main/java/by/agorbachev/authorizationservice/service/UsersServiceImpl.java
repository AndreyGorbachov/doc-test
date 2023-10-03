package by.agorbachev.authorizationservice.service;

import by.agorbachev.authorizationservice.dao.UsersRepository;
import by.agorbachev.authorizationservice.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Iterable<User> getAllUsers() {
		return usersRepo.findAll();
	}

	@Override
	public User getById(Long userId) throws UsernameNotFoundException {
		Optional<User> userOpt = usersRepo.findById(userId);
		if (userOpt.isPresent()) {
			return userOpt.get();
		} else {
			throw new UsernameNotFoundException("User with id " + userId + " not exist");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = usersRepo.findByEmail(username);
		if (userOpt.isPresent()) {
			return userOpt.get();
		} else {
			throw new UsernameNotFoundException("User with email " + username + " not exist");
		}
	}

	@Override
	public User createUser(User newUser) {
		String newUserEmail = newUser.getEmail();
		Optional<User> userOpt = usersRepo.findByEmail(newUserEmail);
		if (userOpt.isEmpty()) {
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
			return usersRepo.save(newUser);
		} else {
			throw new IllegalArgumentException("User with email " + newUserEmail + " exist");
		}
	}

	@Override
	public User updateUser(User newUser, Long userId) throws UsernameNotFoundException {
		Optional<User> userOpt = usersRepo.findById(userId);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setEmail(newUser.getEmail());
			user.setPassword(passwordEncoder.encode(newUser.getPassword()));
			user.setRole(newUser.getRole());
			return usersRepo.save(user);
		} else {
			throw new UsernameNotFoundException("User with id " + userId + " not exist");
		}
	}

	@Override
	public void deleteUserById(Long userId) {
		Optional<User> userOpt = usersRepo.findById(userId);
		if (userOpt.isPresent()) {
			usersRepo.deleteById(userId);
		} else {
			throw new UsernameNotFoundException("User with id " + userId + " not exist");
		}
	}

	/*public String makeGreetingMessageFor(String name) {
		String instanceId = "unknown";
		try {
			instanceId = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			log.warn("can't get hostname", e);
		}
		return String.format(properties.getTemplate(), name, instanceId);
	}*/

}
