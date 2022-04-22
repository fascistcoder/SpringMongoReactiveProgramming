package com.example.springmongo.services;

import com.example.springmongo.commands.RecipeCommand;
import com.example.springmongo.model.Recipe;

import java.util.Set;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 14/09/21
 */
public interface RecipeService {
	Set<Recipe> getRecipes();

	Recipe findById(String id);

	RecipeCommand findCommandById(String id);

	RecipeCommand saveRecipeCommand(RecipeCommand command);

	void deleteById(String idToDelete);
}
