package org.example.web.dto;

import lombok.*;

import javax.validation.constraints.Digits;

@Data
public class Book {
	private String id;
	private String author;
	private String title;

	@Digits(integer = 4, fraction = 0)
	private Integer size;
}
