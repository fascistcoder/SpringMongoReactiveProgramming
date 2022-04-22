package com.example.springmongo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 11/09/21
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = { "recipe" })
public class Notes {
	@Id
	private String id;

	private Recipe recipe;
	private String recipeNotes;
}
