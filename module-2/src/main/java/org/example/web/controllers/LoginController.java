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

	private final Logger log = Logger.getLogger(BookShelfController.class);
	private final LoginService loginService;

	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping
	public String login(Model model) {
		log.info("GET /login returns login_page.html");
		model.addAttribute("loginForm", new LoginForm());
		return "login_page";
	}

	@PostMapping("/auth")
	public String authenticate(LoginForm loginFrom) {
		if (loginService.authenticate(loginFrom)) {
			log.info("login OK redirect to book shelf");
			return "redirect:/books/shelf";
		} else {
			log.info("login FAIL redirect back to login");
			return "redirect:/login";
		}
	}
}
