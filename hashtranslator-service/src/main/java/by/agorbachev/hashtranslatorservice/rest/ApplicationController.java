package by.agorbachev.hashtranslatorservice.rest;

import by.agorbachev.hashtranslatorservice.model.Application;
import by.agorbachev.hashtranslatorservice.model.ApplicationRequest;
import by.agorbachev.hashtranslatorservice.model.ApplicationResult;
import by.agorbachev.hashtranslatorservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

	private final ApplicationService applicationService;

	@GetMapping
	public List<Application> getAll() {
		return applicationService.getAll();
	}

	@GetMapping("/{id}")
	public ApplicationResult getApplicationResult(@PathVariable String id) {
		return applicationService.getApplicationResult(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String doApplication(@RequestBody ApplicationRequest request) {
		return applicationService.doApplication(request);
	}

}
