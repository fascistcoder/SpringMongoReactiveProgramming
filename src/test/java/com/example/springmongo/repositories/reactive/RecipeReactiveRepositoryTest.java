package com.example.springmongo.repositories.reactive;

import com.example.springmongo.model.Recipe;
import com.example.springmongo.repositories.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 22/04/22
 */
@Disabled
@DataMongoTest
public class RecipeReactiveRepositoryTest {

	@Autowired RecipeReactiveRepository recipeReactiveRepository;

	@BeforeEach
	void setup() throws Exception{
		recipeReactiveRepository.deleteAll().block();
	}

	@Test
	void testRecipeSave() throws Exception{
		Recipe recipe = new Recipe();
		recipe.setDescription("Yummy");

		recipeReactiveRepository.save(recipe).block();

		Long count = recipeReactiveRepository.count().block();

		assertEquals(Long.valueOf(1L), count);
	}
}
