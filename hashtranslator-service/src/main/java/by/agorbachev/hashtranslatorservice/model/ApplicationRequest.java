package by.agorbachev.hashtranslatorservice.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ApplicationRequest implements Serializable {

	List<String> hashes;

}
