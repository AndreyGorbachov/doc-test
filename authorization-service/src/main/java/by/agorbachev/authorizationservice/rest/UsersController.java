package by.agorbachev.authorizationservice.rest;

import by.agorbachev.authorizationservice.model.User;
import by.agorbachev.authorizationservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiController.BASE_URL)
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@GetMapping()
	public Iterable<User> getAllUsers() {
		return usersService.getAllUsers();
	}

	@GetMapping("/{userId}")
	public User getUserById(@PathVariable Long userId) {
		return usersService.getById(userId);
	}

	@PostMapping("/admin/")
	public User createUser(@RequestBody User newUser) {
		return usersService.createUser(newUser);
	}

	@PutMapping("/admin/{userId}")
	public User updateUser(@RequestBody User newUser, @PathVariable Long userId) {
		return usersService.updateUser(newUser, userId);
	}

	@DeleteMapping("/admin/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		usersService.deleteUserById(userId);
	}

}
