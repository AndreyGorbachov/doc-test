package by.agorbachev.hashtranslatorservice.dao;

import by.agorbachev.hashtranslatorservice.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationRepository extends MongoRepository<Application, String> {
}
