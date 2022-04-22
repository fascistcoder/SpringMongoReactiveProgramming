package com.example.springmongo.repositories;

import com.example.springmongo.model.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 22/04/22
 */
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
