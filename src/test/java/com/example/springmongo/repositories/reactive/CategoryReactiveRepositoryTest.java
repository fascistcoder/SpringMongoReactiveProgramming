package com.example.springmongo.repositories.reactive;

import com.example.springmongo.model.Category;
import com.example.springmongo.repositories.CategoryReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 22/04/22
 */
@Disabled
@DataMongoTest
public class CategoryReactiveRepositoryTest {

	public static final String FOO = "Foo";

	@Autowired CategoryReactiveRepository categoryReactiveRepository;

	@BeforeEach
	void setUp() throws Exception {
		categoryReactiveRepository.deleteAll().block();
	}

	@Test
	void testSave() throws Exception {
		Category category = new Category();
		category.setDescription("Foo");

		categoryReactiveRepository.save(category).block();

		Long count = categoryReactiveRepository.count().block();

		assertEquals(Long.valueOf(1L), count);
	}

	@Test
	void testFindByDescription() throws Exception {
		Category category = new Category();
		category.setDescription("Foo");

		categoryReactiveRepository.save(category).then().block();

		Category fetchedCat = categoryReactiveRepository.findByDescription("Foo").block();

		assertNotNull(fetchedCat.getId());
	}

}
