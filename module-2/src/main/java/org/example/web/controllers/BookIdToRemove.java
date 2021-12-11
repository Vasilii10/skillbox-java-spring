package org.example.web.controllers;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class BookIdToRemove {

	/* Valid if id is not null*/
	@NotEmpty
	private String id;
}
