package demo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Josh Long
 */
public class SimpleUserDetailsService implements UserDetailsService {

	private final Map<String, User> users = new ConcurrentHashMap<>();

	public SimpleUserDetailsService() {
		List<GrantedAuthority> grantedAuthorities =
			Arrays.asList("openid", "read", "write")
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		Stream.of("dsyer", "sgibb", "pwebb", "rwinch", "rjohnson" ,"jlong")
				.map(x -> new User(x, "password", grantedAuthorities))
				.forEach(u -> this.users.put(u.getUsername(), u));

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!this.users.containsKey(username))
			throw new UsernameNotFoundException("couldn't find user " + username + "!");

		return this.users.get(username);
	}
}
