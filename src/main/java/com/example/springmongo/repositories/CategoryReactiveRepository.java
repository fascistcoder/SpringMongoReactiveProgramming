package com.example.springmongo.repositories;

import com.example.springmongo.model.Category;
import com.example.springmongo.model.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 22/04/22
 */
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {
	Mono<Category> findByDescription(String description);
}
