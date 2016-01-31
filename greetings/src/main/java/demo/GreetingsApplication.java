package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

import static demo.GreetingsApplication.*;

@EnableDiscoveryClient
@SpringBootApplication
public class GreetingsApplication {

	public static final String HI_MAPPING = "/hi";

	public static void main(String[] args) {
		SpringApplication.run(GreetingsApplication.class, args);
	}
}

@RestController
class GreetingsRestController {

	@RequestMapping(method = RequestMethod.GET, value = HI_MAPPING)
	public Map<String, String> message() {
		return Collections.singletonMap("message",
				String.format("Hi, world %s!", System.currentTimeMillis()));
	}
}

@Configuration
@EnableResourceServer
class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
				.antMatcher(HI_MAPPING)
				.authorizeRequests().anyRequest().authenticated();
		// @formatter:on
	}
}
