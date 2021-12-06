package org.example.web.controllers;


import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

	private final Logger log = Logger.getLogger(BookShelfController.class);
	private final BookService bookService;

	@Autowired
	public BookShelfController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/shelf")
	public String books(Model model) {
		log.info("got book shelf");
		model.addAttribute("book", new Book());
		model.addAttribute("bookList", bookService.getAllBooks());

		return "book_shelf";
	}

	@PostMapping("/save")
	public String saveBook(Book book) {
		if (bookService.saveBook(book)) {
			log.info("current repository size: " + bookService.getAllBooks().size());

			return "redirect:/books/shelf";
		} else {
			log.warn("Attempt to save empty book");

			return "redirect:/books/shelf";
		}
	}

	@PostMapping("/remove")
	public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
		if (bookService.removeBookById(bookIdToRemove)) {
			log.info("Book with id {" + bookIdToRemove + "} successfully deleted");

			return "redirect:/books/shelf";
		} else {
			log.warn("Book with id {" + bookIdToRemove + "} was not deleted");

			return "redirect:/books/shelf";
		}
	}

	@PostMapping("/removeByRegex")
	public String removeBookByRegex(@RequestParam("userRegex") String userRegex) {
		if (bookService.removeBookByRegex(userRegex)) {
			log.info("Executing deletion by user's regex: " + userRegex);

			return "redirect:/books/shelf";
		} else {
			log.error("Error during deletion by regex");

			return "redirect:/books/shelf";
		}
	}
}
