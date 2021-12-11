package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ErrorController {

	private final Logger log = Logger.getLogger(ErrorController.class);

	/* This method will not work with @ControllerAdvice
	* Only works with @Controller */
	@GetMapping("/getNotFoundError")
	public String getNotFoundError() {
		log.info("Calling getNotFoundError method in ErrorController");

		return "errors/404";
	}

	@ExceptionHandler(BookShelfLoginException.class)
	public String handleError(Model modelAndView, Exception e) {
		log.info("Calling handleError method in ErrorController");
		modelAndView.addAttribute("errorMessage", e.getMessage());

		return "errors/404";
	}
}