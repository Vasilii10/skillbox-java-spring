package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.*;

@Service
public class BookService {

	private final Logger LOG = Logger.getLogger(BookService.class);
	private final BookRepository<Book> bookRepo;

	@Autowired
	public BookService(BookRepository<Book> bookRepo) {
		this.bookRepo = bookRepo;
	}

	public List<Book> getAllBooks() {
		return bookRepo.retrieveAll();
	}

	public boolean saveBook(Book book) {
		if (validateBookData(book)) {
			LOG.info("Book data is valid");

			return bookRepo.store(book);
		} else {
			LOG.info("Book data is invalid");

			return false;
		}
	}

	private boolean validateBookData(Book book) {
		return !book.getAuthor().isEmpty() || book.getSize() != null || !book.getTitle().isEmpty();
	}

	public boolean removeBookById(Integer bookIdToRemove) {
		return bookRepo.removeItemById(bookIdToRemove);
	}

	public boolean removeBookByRegex(String inputUserRegex) {
		Matcher matcher = Pattern.compile("([^=]+)").matcher(inputUserRegex);

		if (matcher.find()) {

			String deletionField = matcher.group(1);
			String finalData = inputUserRegex.replace(deletionField + "=", "");

			if (deletionField.contains("author")) {
				LOG.info("Executing deleting by author");

				return bookRepo.removeItemsByAuthor(finalData);
			} else if (deletionField.contains("title")) {
				LOG.info("Executing deletion by title");

				return bookRepo.removeItemsByTitle(finalData);
			} else if (deletionField.contains("size")) {
				LOG.info("Executing deletion by size");

				return bookRepo.removeItemsBySize(finalData);
			} else {
				LOG.error("User's regex does not contain any deletion fields: " + deletionField);

				return false;
			}
		} else {
			LOG.error("User's regex is not valid: " + inputUserRegex);

			return false;
		}
	}

	/*Default method for all configuration*/
	private void defaultInit() {
		LOG.info("defaultInit " + getClass().getSimpleName());
	}

	/*Default method for all configuration*/
	private void defaultDestroy() {
		LOG.info("defaultDestroy " + getClass().getSimpleName());
	}
}
