package com.example.springmongo.config;

import com.example.springmongo.model.Recipe;
import com.example.springmongo.services.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 24/04/22
 */
@Configuration
public class WebConfig {

	@Bean public RouterFunction<?> routes(RecipeService recipeService) {
		return RouterFunctions.route(GET("/api/recipes"),
				serverRequest -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(recipeService.getRecipes(), Recipe.class));
	}
}
