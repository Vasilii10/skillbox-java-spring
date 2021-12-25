package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	private final Logger LOG = Logger.getLogger(LoginService.class);

	public boolean authenticate(LoginForm loginFrom) {
		LOG.info("try auth with user-form: " + loginFrom);
		return loginFrom.getUsername().equals("root") && loginFrom.getPassword().equals("123");
	}
}
