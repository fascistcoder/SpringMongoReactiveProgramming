package com.example.springmongo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 20/09/21
 */
class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp(){
        category= new Category();
    }

    @Test
    void getId() throws Exception{
        String idValue = "4";
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipes() {
    }
}