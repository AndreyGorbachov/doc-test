package by.agorbachev.authorizationservice.dao;

import by.agorbachev.authorizationservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
	Optional<User> findByEmail(String email);

}
