package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository implements ProjectRepository<Book> {

	private final Logger LOG = Logger.getLogger(BookRepository.class);
	private final List<Book> repo = new ArrayList<>();

	@Override
	public List<Book> retrieveAll() {
		return new ArrayList<>(repo);
	}

	@Override
	public boolean store(Book book) {
		book.setId(book.hashCode());
		LOG.info("store new book: " + book);
		repo.add(book);

		return true;
	}

	@Override
	public boolean removeItemById(Integer boolId) {
		for (Book book : retrieveAll()) {
			if (book.getId().equals(boolId)) {
				LOG.info("remove book { " + book + " } completed");
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
				LOG.info("remove book { " + book + " } completed");
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
				LOG.info("remove book { " + book + " } completed");
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
				LOG.info("remove book { " + book + " } completed");
				result = repo.remove(book);
			}
		}
		return result;
	}
}
