package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.controllers.BookShelfController;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.*;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository implements ProjectRepository<Book> , ApplicationContextAware {

	private final Logger log = Logger.getLogger(BookShelfController.class);
	private final List<Book> repo = new ArrayList<>();
	private ApplicationContext context;

	@Override
	public List<Book> retrieveAll() {
		return new ArrayList<>(repo);
	}

	@Override
	public boolean store(Book book) {
		book.setId(context.getBean(IdProvider.class).provideId(book));
		log.info("store new book: " + book);
		repo.add(book);

		return true;
	}

	@Override
	public boolean removeItemById(String boolId) {
		for (Book book : retrieveAll()) {
			if (book.getId().equals(boolId)) {
				log.info("remove book { " + book + " } completed");
				return repo.remove(book);
			}
		}
		return false;
	}

	@Override
	public boolean removeItemsByAuthor(String author) {
		boolean result = false;
		for (Book book : retrieveAll()) {
			if (book.getAuthor().equals(author)) {
				log.info("remove book { " + book + " } completed");
				result = repo.remove(book);
			}
		}
		return result;
	}

	@Override
	public boolean removeItemsByTitle(String title) {
		boolean result = false;

		for (Book book : retrieveAll()) {
			if (book.getTitle().equals(title)) {
				log.info("remove book { " + book + " } completed");
				result = repo.remove(book);
			}
		}
		return result;
	}

	@Override
	public boolean removeItemsBySize(String size) {
		boolean result = false;

		for (Book book : retrieveAll()) {
			if (book.getSize() == Integer.parseInt(size)) {
				log.info("remove book { " + book + " } completed");
				result = repo.remove(book);
			}
		}
		return result;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
