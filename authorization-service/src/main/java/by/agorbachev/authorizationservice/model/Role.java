package by.agorbachev.authorizationservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public enum Role {

	USER("USER"),
	ADMIN("ADMIN");

	private static final String ROLE_PREFIX = "ROLE_";
	@Getter
	private final String name;

	public List<SimpleGrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + this.name()));
	}

}
