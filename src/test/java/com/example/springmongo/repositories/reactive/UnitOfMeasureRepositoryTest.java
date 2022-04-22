package com.example.springmongo.repositories.reactive;

import com.example.springmongo.model.UnitOfMeasure;
import com.example.springmongo.repositories.UnitOfMeasureReactiveRepository;
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
public class UnitOfMeasureRepositoryTest{

	public static final String EACH = "Each";

	@Autowired UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureReactiveRepository.deleteAll().block();
	}

	@Test
	void testSaveUom() throws Exception {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(EACH);

		unitOfMeasureReactiveRepository.save(uom).block();

		Long count = unitOfMeasureReactiveRepository.count().block();

		assertEquals(Long.valueOf(1L), count);

	}

	@Test
	void testFindByDescription() throws Exception {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(EACH);

		unitOfMeasureReactiveRepository.save(uom).block();

		UnitOfMeasure fetchedUOM = unitOfMeasureReactiveRepository.findByDescription(EACH).block();

		assertEquals(EACH, fetchedUOM.getDescription());

	}
}
