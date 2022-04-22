package com.example.springmongo.services;

import com.example.springmongo.commands.IngredientCommand;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 30/09/21
 */
public interface IngredientService {
    IngredientCommand findByRecipeAndIngredientId(String recipeId, String ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
    void deleteById(String recipeId, String idToDelete);
}
