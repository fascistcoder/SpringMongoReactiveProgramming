package com.example.springmongo.controllers;

import com.example.springmongo.config.WebConfig;
import com.example.springmongo.model.Recipe;
import com.example.springmongo.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.*;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 24/04/22
 */
public class RouterFunctionTest {

	WebTestClient webTestClient;

	@Mock RecipeService recipeService;

	@BeforeEach
	void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);

		WebConfig webConfig = new WebConfig();

		RouterFunction<?> routerFunction = webConfig.routes(recipeService);

		webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
	}

	@Test
	void testGetRecipes() throws Exception{
		when(recipeService.getRecipes()).thenReturn(Flux.just());

		webTestClient.get().uri("/api/recipes")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	void testGetRecipesWithData() throws Exception{
		when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe(), new Recipe()));

		webTestClient.get().uri("/api/recipes")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(Recipe.class);
	}
}
