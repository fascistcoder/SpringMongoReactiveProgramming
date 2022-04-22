package com.example.springmongo.services;


import com.example.springmongo.commands.RecipeCommand;
import com.example.springmongo.converters.RecipeCommandToRecipe;
import com.example.springmongo.converters.RecipeToRecipeCommand;
import com.example.springmongo.exceptions.NotFoundException;
import com.example.springmongo.model.Recipe;
import com.example.springmongo.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipeByIdTest() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById("1");

        assertNotNull("Null recipe returned",recipeReturned);
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository,never()).findAll();
    }

    @Test
    void getRecipeByIDTestNotFound() throws Exception{
        Exception exception = Assertions.assertThrows(NotFoundException.class, ()->{
            Optional<Recipe> recipeOptional = Optional.empty();

            when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

            Recipe recipe = recipeService.findById("1");
        });
    }

    @Test
    void getRecipeCommandByIdTest() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand recipeCommandById = recipeService.findCommandById("1");

        assertNotNull("Null recipe returned",recipeCommandById);
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository,never()).findAll();
    }

    @Test
    void getRecipes() throws Exception{
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipesData);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository,never()).findById(anyString());
    }

    @Test
    void testDeleteById() throws Exception{
        String idToDelete = "1";

        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(anyString());
    }
}