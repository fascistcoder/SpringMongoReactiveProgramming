package com.example.springmongo.services;

import com.example.springmongo.commands.IngredientCommand;
import com.example.springmongo.converters.IngredientCommandToIngredient;
import com.example.springmongo.converters.IngredientToIngredientCommand;
import com.example.springmongo.model.Ingredient;
import com.example.springmongo.model.Recipe;
import com.example.springmongo.repositories.RecipeRepository;
import com.example.springmongo.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	@Override
	public IngredientCommand findByRecipeAndIngredientId(String recipeId, String ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (!recipeOptional.isPresent()) {
			log.error("recipe id not found. ID: " + recipeId);
		}

		Recipe recipe = recipeOptional.get();

		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

		if (!ingredientCommandOptional.isPresent()) {
			log.error("recipe id not found. ID: " + ingredientId);
		}

		IngredientCommand ingredientCommand = ingredientCommandOptional.get();
		ingredientCommand.setRecipeId(recipe.getId());

		return ingredientCommandOptional.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

		if (!recipeOptional.isPresent()) {

			//todo toss error if not found!
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();

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
						.findById(command.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
			} else {
				//add new Ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}

			Recipe savedRecipe = recipeRepository.save(recipe);

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

			return ingredientCommandSaved;
		}

	}

	@Override
	public void deleteById(String recipeId, String idToDelete) {
		log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (recipeOptional.isPresent()) {
			Recipe recipe = recipeOptional.get();
			log.debug("found recipe");

			Optional<Ingredient> ingredientOptional = recipe.getIngredients()
					.stream().filter(ingredient -> ingredient.getId().equals(idToDelete))
					.findFirst();
			if (ingredientOptional.isPresent()) {
				log.debug("found ingredient");
				Ingredient ingredientToDelete = ingredientOptional.get();
				ingredientToDelete.setRecipe(null);
				recipe.getIngredients().remove(ingredientOptional.get());
				recipeRepository.save(recipe);
			}
		} else {
			log.debug("Recipe ID not found. ID: " + recipeId);
		}
	}
}
