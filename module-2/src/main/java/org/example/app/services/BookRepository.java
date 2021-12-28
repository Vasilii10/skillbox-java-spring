package org.example.app.services;

import java.util.List;

public interface BookRepository<T> {

	List<T> retrieveAll();

	boolean store(T book);

	boolean removeItemById(Integer bookId);

	boolean removeItemsByAuthor(String author);

	boolean removeItemsByTitle(String title);

	boolean removeItemsBySize(String size);
}
