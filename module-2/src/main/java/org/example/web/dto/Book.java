package org.example.web.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class Book {

	private Integer id;

	@NotNull
	private String author;

	@NotNull
	private String title;

	@Digits(integer = 4, fraction = 0)
	private Integer size;
}
