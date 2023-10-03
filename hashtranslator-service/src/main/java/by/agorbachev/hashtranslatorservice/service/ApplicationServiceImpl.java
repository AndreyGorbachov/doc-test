package by.agorbachev.hashtranslatorservice.service;

import by.agorbachev.hashtranslatorservice.dao.ApplicationRepository;
import by.agorbachev.hashtranslatorservice.exception.ExceptionMessages;
import by.agorbachev.hashtranslatorservice.model.Application;
import by.agorbachev.hashtranslatorservice.model.ApplicationRequest;
import by.agorbachev.hashtranslatorservice.model.ApplicationResult;
import by.agorbachev.hashtranslatorservice.model.ApplicationStatus;
import by.agorbachev.hashtranslatorservice.model.HashProcessing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Value("${decoder.URL}")
	private String decoderURL;
	@Value("${decoder.login}")
	private String decoderLogin;
	@Value("${decoder.password}")
	private String decoderPassword;

	private final ApplicationRepository applicationRepo;

	private final RestTemplate restTemplate;

	@Override
	public String doApplication(ApplicationRequest request) {
		final Application application = createNewApplication(request.getHashes());
		runApplicationProcessing(application);
		return application.getId();
	}

	private void runApplicationProcessing(Application application) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<? extends Future<?>> futures = application.getHashes().stream()
				.map(hashProcessing ->
						// Executing with multithreading
						executorService.submit(() -> {
							Map<String, String> vars = new HashMap<>();
							vars.put("email", decoderLogin);
							vars.put("key", decoderPassword);
							vars.put("hash", hashProcessing.getHash());

							String result = restTemplate.getForObject(decoderURL, String.class, vars);

							hashProcessing.setResult(result);
						})).toList();
		// Wait processing result
		executorService.submit(() -> {
			boolean readyResult = false;
			while (!readyResult) {
				if (futures.stream().allMatch(Future::isDone)) {
					readyResult = true;
					application.setStatus(ApplicationStatus.DONE);
					log.debug("Applications is done. Result: " + application.getHashes());
					applicationRepo.save(application);
				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});
	}

	private Application createNewApplication(List<String> hashes) {
		Set<HashProcessing> hashProcessingList = hashes.stream().map(hash -> new HashProcessing(hash, "")).collect(Collectors.toSet());
		return applicationRepo.save(new Application(ApplicationStatus.IN_PROCESS, hashProcessingList));
	}

	@Override
	public ApplicationResult getApplicationResult(String appId) {
		Optional<Application> appOpt = applicationRepo.findById(appId);
		if (appOpt.isPresent()) {
			Application application = appOpt.get();
			if (application.getStatus() == ApplicationStatus.DONE) {
				return new ApplicationResult(application.getHashes());
			} else {
				throw new ResponseStatusException(HttpStatus.PROCESSING,
						MessageFormat.format(ExceptionMessages.APPLICATION_IN_PROCESS, appId));
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					MessageFormat.format(ExceptionMessages.APPLICATION_NOT_FOUND, appId));
		}
	}

	@Override
	public List<Application> getAll() {
		return applicationRepo.findAll();
	}

}
