package org.example.web.dto;

import lombok.Data;

@Data
public class Book {
	private String id;
	private String author;
	private String title;
	private Integer size;
}
