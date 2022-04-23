package com.example.springmongo.controllers;

import com.example.springmongo.commands.IngredientCommand;
import com.example.springmongo.commands.RecipeCommand;
import com.example.springmongo.commands.UnitOfMeasureCommand;
import com.example.springmongo.services.IngredientService;
import com.example.springmongo.services.RecipeService;
import com.example.springmongo.services.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 29/09/21
 */
@Slf4j
@Controller
@AllArgsConstructor
public class IngredientController {
	public final RecipeService recipeService;
	public final IngredientService ingredientService;
	public final UnitOfMeasureService unitOfMeasureService;

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("getting ingredient list for recipe id: " + recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(recipeId));
		return "recipe/ingredient/list";
	}

	@GetMapping("recipe/{recipeid}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeid, @PathVariable String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeAndIngredientId(recipeid, id).block());
		return "recipe/ingredient/show";
	}

	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();

		IngredientCommand ingredientCommand = new IngredientCommand();

		model.addAttribute("ingredient", ingredientCommand);

		ingredientCommand.setUom(new UnitOfMeasureCommand());
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms().collectList().block());
		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,
			@PathVariable String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeAndIngredientId(recipeId, id).block());

		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		return "recipe/ingredient/ingredientform";
	}

	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();


		log.debug("saved ingredient id:" + savedCommand.getId());

		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteRecipeIngredient(@PathVariable String recipeId, @PathVariable String id) {
		log.debug("Deleting id: " + recipeId);
		ingredientService.deleteById(recipeId, id).block();
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
}
