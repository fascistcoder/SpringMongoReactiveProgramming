package com.example.springmongo.converters;

import com.example.springmongo.commands.IngredientCommand;
import com.example.springmongo.model.Ingredient;
import com.example.springmongo.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 27/09/21
 */

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
		this.uomConverter = uomConverter;
	}

	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
			return null;
		}

		final Ingredient ingredient = new Ingredient();
		ingredient.setId(source.getId());

		if (source.getRecipeId() != null) {
			Recipe recipe = new Recipe();
			recipe.setId(source.getRecipeId());

			recipe.addIngredient(ingredient);
		}

		ingredient.setAmount(source.getAmount());
		ingredient.setDescription(source.getDescription());
		ingredient.setUom(uomConverter.convert(source.getUom()));
		return ingredient;
	}
}
