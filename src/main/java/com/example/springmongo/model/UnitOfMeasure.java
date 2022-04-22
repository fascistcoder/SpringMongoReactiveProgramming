package com.example.springmongo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 11/09/21
 */
@Getter
@Setter
@Document
public class UnitOfMeasure {

	@Id
	private String id;

	private String description;

}
