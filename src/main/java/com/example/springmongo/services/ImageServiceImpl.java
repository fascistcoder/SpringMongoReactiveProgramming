package com.example.springmongo.services;

import com.example.springmongo.model.Recipe;
import com.example.springmongo.repositories.RecipeReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 01/10/21
 */
@Slf4j
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final RecipeReactiveRepository recipeReactiveRepository;

	@Override
	@Transactional
	public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {

		Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId)
				.map(recipe -> {
					Byte[] byteObjects = new Byte[0];
					try {
						byteObjects = new Byte[file.getBytes().length];
						int i = 0;

						for (byte b : file.getBytes()) {
							byteObjects[i++] = b;
						}

						recipe.setImage(byteObjects);

						return recipe;
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				});

		recipeReactiveRepository.save(recipeMono.block()).block();

		return Mono.empty();
	}
}
