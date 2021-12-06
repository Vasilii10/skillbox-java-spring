package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.controllers.BookShelfController;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.*;

@Service
public class BookService {

	private final Logger log = Logger.getLogger(BookShelfController.class);
	private final ProjectRepository<Book> bookRepo;

	@Autowired
	public BookService(ProjectRepository<Book> bookRepo) {
		this.bookRepo = bookRepo;
	}

	public List<Book> getAllBooks() {
		return bookRepo.retrieveAll();
	}

	public boolean saveBook(Book book) {
		if (validateBookData(book)) {
			log.info("Book data is valid");

			return bookRepo.store(book);
		} else {
			log.info("Book data is invalid");

			return false;
		}
	}

	private boolean validateBookData(Book book) {
		return !book.getAuthor().isEmpty() || book.getSize() != null || !book.getTitle().isEmpty();
	}

	public boolean removeBookById(String bookIdToRemove) {
		return bookRepo.removeItemById(bookIdToRemove);
	}

	public boolean removeBookByRegex(String inputUserRegex) {
		Matcher matcher = Pattern.compile("([^=]+)").matcher(inputUserRegex);

		if (matcher.find()) {

			String deletionField = matcher.group(1);
			String finalData = inputUserRegex.replace(deletionField + "=", "");

			if (deletionField.contains("author")) {
				log.info("Executing deleting by author");

				return bookRepo.removeItemsByAuthor(finalData);
			} else if (deletionField.contains("title")) {
				log.info("Executing deletion by title");

				return bookRepo.removeItemsByTitle(finalData);
			} else if (deletionField.contains("size")) {
				log.info("Executing deletion by size");

				return bookRepo.removeItemsBySize(finalData);
			} else {
				log.error("User's regex does not contain any deletion fields: " + deletionField);

				return false;
			}
		} else {
			log.error("User's regex is not valid: " + inputUserRegex);

			return false;
		}
	}
}
