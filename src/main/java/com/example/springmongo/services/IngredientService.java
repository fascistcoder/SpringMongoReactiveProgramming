package com.example.springmongo.services;

import com.example.springmongo.commands.IngredientCommand;
import reactor.core.publisher.Mono;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 30/09/21
 */
public interface IngredientService {
    Mono<IngredientCommand> findByRecipeAndIngredientId(String recipeId, String ingredientId);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);
    Mono<Void> deleteById(String recipeId, String idToDelete);
}
