package by.agorbachev.hashtranslatorservice.service;

import by.agorbachev.hashtranslatorservice.model.Application;
import by.agorbachev.hashtranslatorservice.model.ApplicationRequest;
import by.agorbachev.hashtranslatorservice.model.ApplicationResult;

import java.util.List;

public interface ApplicationService {

	String doApplication(ApplicationRequest request);

	ApplicationResult getApplicationResult(String appId);

	List<Application> getAll();

}
