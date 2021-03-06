package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	private final Logger LOG = Logger.getLogger(LoginController.class);
	private final LoginService loginService;

	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping
	public String login(Model model) {
		LOG.info("GET /login returns login_page.html");
		model.addAttribute("loginForm", new LoginForm());
		return "login_page";
	}

	@PostMapping("/auth")
	public String authenticate(LoginForm loginFrom) throws BookShelfLoginException {
		if (loginService.authenticate(loginFrom)) {
			LOG.info("login OK redirect to book shelf");
			return "redirect:/books/shelf";
		} else {
			LOG.info("login FAIL redirect back to login");
			throw new BookShelfLoginException("Invalid user credentials");
		}
	}

}
