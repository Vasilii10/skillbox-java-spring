package org.example.web.controllers;


import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;


@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfResource {

	private final Logger LOG = Logger.getLogger(BookShelfResource.class);
	private final BookService bookService;

	@Autowired
	public BookShelfResource(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/shelf")
	public String books(Model model) {
		LOG.info(this.toString());
		model.addAttribute("book", new Book());
		model.addAttribute("bookIdToRemove", new BookIdToRemove());
		model.addAttribute("bookList", bookService.getAllBooks());

		return "book_shelf";
	}

	@PostMapping("/save")
	public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {

			LOG.warn("Ошибка" + bindingResult.getAllErrors());
			LOG.warn("Book field 'size' contains invalid data!");

			model.addAttribute("book", book);
			model.addAttribute("bookIdToRemove", new BookIdToRemove());
			model.addAttribute("bookList", bookService.getAllBooks());

			return "book_shelf";
		} else {
			if (bookService.saveBook(book)) {
				LOG.info("current repository size: " + bookService.getAllBooks().size());
			} else {
				LOG.warn("Attempt to save empty book");
			}
			return "redirect:/books/shelf";
		}
	}

	@PostMapping("/remove")
	public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			LOG.warn("BookIdToRemove contains invalid data!");
			model.addAttribute("book", new Book());
			model.addAttribute("bookList", bookService.getAllBooks());

			return "book_shelf";
		} else {
			if (bookService.removeBookById(bookIdToRemove.getId())) {
				LOG.info("Book with id {" + bookIdToRemove + "} successfully deleted");
			} else {
				LOG.warn("Book with id {" + bookIdToRemove + "} was not deleted");
			}

			return "redirect:/books/shelf";
		}
	}

	@PostMapping("/removeByRegex")
	public String removeBookByRegex(@RequestParam("userRegex") String userRegex) {
		if (bookService.removeBookByRegex(userRegex)) {
			LOG.info("Executing deletion by user's regex: " + userRegex);

			return "redirect:/books/shelf";
		} else {
			LOG.error("Error during deletion by regex");

			return "redirect:/books/shelf";
		}
	}

	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		File fileUploadsDir = getDirectoryForFileUploads();

		File serverFile = new File(fileUploadsDir + "/" + fileName);

		try (FileOutputStream fos = new FileOutputStream(serverFile)) {
			fos.write(file.getBytes());

			LOG.info("Uploaded file { " + fileName + "} saved successfully");
		} catch (Exception e) {
			LOG.error("Error during saving file on server");
		}

		return "redirect:/books/shelf";
	}

	private File getDirectoryForFileUploads() {
		String serverRootPath = System.getProperty("catalina.home");
		File directory = new File(serverRootPath + File.separator + "external_uploads");
		if (!directory.exists()) {
			directory.mkdirs();
		}

		return directory;
	}

}
