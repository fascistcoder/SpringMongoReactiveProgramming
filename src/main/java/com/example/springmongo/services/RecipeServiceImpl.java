package com.example.springmongo.services;

import com.example.springmongo.commands.RecipeCommand;
import com.example.springmongo.converters.RecipeCommandToRecipe;
import com.example.springmongo.converters.RecipeToRecipeCommand;
import com.example.springmongo.model.Recipe;
import com.example.springmongo.repositories.RecipeReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 14/09/21
 */
@Slf4j
@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

	private final RecipeReactiveRepository recipeReactiveRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	@Override
	public Flux<Recipe> getRecipes() {
		log.debug("I'm in the service");
		return recipeReactiveRepository.findAll();
	}

	@Override
	public Mono<Recipe> findById(String id) {
		return recipeReactiveRepository.findById(id);
	}

	@Override
	public Mono<RecipeCommand> findCommandById(String id) {
		return recipeReactiveRepository.findById(id)
				.map(recipe -> {
					RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

					recipeCommand.getIngredients().forEach(rc -> {
						rc.setRecipeId(recipeCommand.getId());
					});
					return recipeCommand;
				});
	}

	@Override
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
		return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command))
				.map(recipeToRecipeCommand::convert);
	}

	@Override
	public Mono<Void> deleteById(String idToDelete) {
		recipeReactiveRepository.deleteById(idToDelete);
		return Mono.empty();
	}
}
