package com.example.springmongo.services;

import com.example.springmongo.commands.IngredientCommand;
import com.example.springmongo.converters.IngredientCommandToIngredient;
import com.example.springmongo.converters.IngredientToIngredientCommand;
import com.example.springmongo.model.Ingredient;
import com.example.springmongo.model.Recipe;
import com.example.springmongo.repositories.RecipeReactiveRepository;
import com.example.springmongo.repositories.UnitOfMeasureReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 30/09/21
 */
@Slf4j
@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeReactiveRepository recipeReactiveRepository;
	private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

	@Override
	public Mono<IngredientCommand> findByRecipeAndIngredientId(String recipeId, String ingredientId) {

		return recipeReactiveRepository
				.findById(recipeId)
				.flatMapIterable(Recipe::getIngredients)
				.filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
				.single()
				.map(ingredient -> {
					IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
					assert command != null;
					command.setRecipeId(recipeId);
					return command;
				});
	}

	@Override
	@Transactional
	public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
		Recipe recipe = recipeReactiveRepository.findById(command.getRecipeId()).block();

		if (recipe == null) {

			//todo toss error if not found!
			log.error("Recipe not found for id: " + command.getRecipeId());
			return Mono.just(new IngredientCommand());
		} else {

			Optional<Ingredient> ingredientOptional = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId()))
					.findFirst();

			if (ingredientOptional.isPresent()) {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				ingredientFound.setUom(unitOfMeasureRepository
						.findById(command.getUom().getId()).block());
				if (ingredientFound.getUom() == null) {
					new RuntimeException("Uom not Found");
				}
			} else {
				//add new Ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				recipe.addIngredient(ingredient);
			}

			Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
					.findFirst();

			if (!savedIngredientOptional.isPresent()) {
				savedIngredientOptional = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
						.findFirst();
			}

			//to do check for fail
			//enhance with id value
			IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
			assert ingredientCommandSaved != null;
			ingredientCommandSaved.setRecipeId(recipe.getId());

			return Mono.just(ingredientCommandSaved);
		}

	}

	@Override
	public Mono<Void> deleteById(String recipeId, String idToDelete) {
		log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

		Recipe recipe= recipeReactiveRepository.findById(recipeId).block();

		if (recipe != null) {
			log.debug("found recipe");

			Optional<Ingredient> ingredientOptional = recipe.getIngredients()
					.stream().filter(ingredient -> ingredient.getId().equals(idToDelete))
					.findFirst();
			if (ingredientOptional.isPresent()) {
				log.debug("found ingredient");
				Ingredient ingredientToDelete = ingredientOptional.get();
				ingredientToDelete.setRecipe(null);
				recipe.getIngredients().remove(ingredientOptional.get());
				recipeReactiveRepository.save(recipe);
			}
		} else {
			log.debug("Recipe ID not found. ID: " + recipeId);
		}
		return Mono.empty();
	}
}
