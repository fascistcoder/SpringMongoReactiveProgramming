package com.example.springmongo.services;

import com.example.springmongo.commands.RecipeCommand;
import com.example.springmongo.converters.RecipeCommandToRecipe;
import com.example.springmongo.converters.RecipeToRecipeCommand;
import com.example.springmongo.exceptions.NotFoundException;
import com.example.springmongo.model.Recipe;
import com.example.springmongo.repositories.RecipeReactiveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 20/09/21
 */
class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;

	@Mock
	RecipeReactiveRepository recipeReactiveRepository;

	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	void getRecipeByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		Recipe recipeReturned = recipeService.findById("1").block();

		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}

	@Test
	void getRecipeCommandByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");

		when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

		RecipeCommand recipeCommandById = recipeService.findCommandById("1").block();

		assertNotNull("Null recipe returned", recipeCommandById);
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}

	@Test
	void getRecipes() throws Exception {
		Recipe recipe = new Recipe();
		HashSet recipesData = new HashSet();
		recipesData.add(recipe);

		when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));

		List<Recipe> recipes = recipeService.getRecipes().collectList().block();

		assertEquals(recipes.size(), 1);
		verify(recipeReactiveRepository, times(1)).findAll();
		verify(recipeReactiveRepository, never()).findById(anyString());
	}

	@Test
	void testDeleteById() throws Exception {
		String idToDelete = "1";

		recipeService.deleteById(idToDelete);

		verify(recipeReactiveRepository, times(1)).deleteById(anyString());
	}
}