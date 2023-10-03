package by.agorbachev.hashtranslatorservice.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@RequiredArgsConstructor
@Document("applications")
public class Application {

	@Id
	private String id;
	@NonNull
	private ApplicationStatus status;
	@NonNull
	private Set<HashProcessing> hashes;

}
