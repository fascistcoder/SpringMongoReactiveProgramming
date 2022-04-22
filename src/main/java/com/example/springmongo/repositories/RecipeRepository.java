package com.example.springmongo.repositories;

import com.example.springmongo.model.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 13/09/21
 */
public interface RecipeRepository extends CrudRepository<Recipe, String> {

}
