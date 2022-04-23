package com.example.springmongo.services;

import com.example.springmongo.commands.IngredientCommand;
import com.example.springmongo.commands.UnitOfMeasureCommand;
import com.example.springmongo.converters.IngredientCommandToIngredient;
import com.example.springmongo.converters.IngredientToIngredientCommand;
import com.example.springmongo.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.example.springmongo.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.springmongo.model.Ingredient;
import com.example.springmongo.model.Recipe;
import com.example.springmongo.repositories.RecipeReactiveRepository;
import com.example.springmongo.repositories.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 01/10/21
 */
class IngredientServiceImplTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	@Mock RecipeReactiveRepository recipeReactiveRepository;

	IngredientService ingredientService;

	@Mock
	UnitOfMeasureReactiveRepository unitOfMeasureRepository;

	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeReactiveRepository,
				unitOfMeasureRepository);
	}

	@Test
	void findByRecipeAndIngredientId() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId("1");

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId("1");

		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId("3");

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		IngredientCommand ingredientCommand = ingredientService.findByRecipeAndIngredientId("1", "3").block();

		assertEquals(("3"), ingredientCommand.getId());
		verify(recipeReactiveRepository, times(1)).findById(anyString());
	}

	@Test
	void testSaveRecipeCommand() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setRecipeId("2");
		command.setUom(new UnitOfMeasureCommand());
		command.getUom().setId("1234");

		Recipe recipe = new Recipe();

		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId("3");

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
		when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

		//when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

		//then
		assertEquals(("3"), savedCommand.getId());
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

	}

	@Test
	void testDeleteById() throws Exception {
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId("3");
		recipe.addIngredient(ingredient);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

		ingredientService.deleteById("1", "3");

		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
	}
}