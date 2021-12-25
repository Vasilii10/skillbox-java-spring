package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.controllers.BookShelfController;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class BookRepositoryImpl implements BookRepository<Book>, ApplicationContextAware {

	private static final Logger LOG = Logger.getLogger(BookShelfController.class);
	private ApplicationContext context;

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public BookRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Book> retrieveAll() {

		List<Book> books = jdbcTemplate.query("SELECT * FROM book", (ResultSet rs, int numRows) ->
		{
			Book book = new Book();
			book.setId(rs.getInt("id_book"));
			book.setAuthor(rs.getString("author"));
			book.setTitle(rs.getString("title"));
			book.setSize(rs.getInt("size"));

			return book;
		});

		return new ArrayList<>(books);
	}

	@Override
	public boolean store(Book book) {
		LOG.info("trying to store new book: " + book);

		MapSqlParameterSource source = new MapSqlParameterSource();
		source.addValue("author", book.getAuthor());
		source.addValue("title", book.getTitle());
		source.addValue("size", book.getSize());

		book.setId(context.getBean(IdProvider.class).provideId(book));

		return jdbcTemplate.update("INSERT INTO book(author, title, size) VALUES (:author, :title, :size)", source) == 1;
	}

	@Override
	public boolean removeItemById(Integer bookId) {
		MapSqlParameterSource source = new MapSqlParameterSource();
		source.addValue("id", bookId);

		if (jdbcTemplate.update("DELETE FROM book WHERE id_book = :id", source) == 1) {
			LOG.info("remove book with id " + bookId + " completed");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeItemsByAuthor(String author) {
		boolean result = false;
		for (Book book : retrieveAll()) {
			if (book.getAuthor().equals(author)) {
				LOG.info("remove book { " + book + " } completed");
				// TODO: 25/12/2021
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
				// TODO: 25/12/2021
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
				// TODO: 25/12/2021
			}
		}

		return result;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
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
