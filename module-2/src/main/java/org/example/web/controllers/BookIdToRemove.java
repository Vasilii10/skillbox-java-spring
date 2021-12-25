package org.example.web.controllers;

import lombok.Data;

import javax.validation.constraints.*;


@Data
public class BookIdToRemove {

	/* Valid if id is not null*/
	@NotNull
	private Integer id;
}
