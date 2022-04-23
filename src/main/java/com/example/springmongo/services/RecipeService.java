package com.example.springmongo.services;

import com.example.springmongo.commands.RecipeCommand;
import com.example.springmongo.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 14/09/21
 */
public interface RecipeService {
	Flux<Recipe> getRecipes();

	Mono<Recipe> findById(String id);

	Mono<RecipeCommand> findCommandById(String id);

	Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

	Mono<Void> deleteById(String idToDelete);
}
