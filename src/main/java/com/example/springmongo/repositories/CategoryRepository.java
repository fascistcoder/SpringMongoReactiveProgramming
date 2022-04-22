package com.example.springmongo.repositories;

import com.example.springmongo.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 13/09/21
 */
public interface CategoryRepository extends CrudRepository<Category, String> {
    Optional<Category> findByDescription(String description);
}
