package com.example.springmongo.converters;


import com.example.springmongo.commands.CategoryCommand;
import com.example.springmongo.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 27/09/21
 */
class CategoryToCategoryCommandTest {

    public static final String ID_VALUE = "1";
    public static final String DESCRIPTION = "descript";
    CategoryToCategoryCommand convter;

    @BeforeEach
    void setUp() throws Exception {
        convter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObject() throws Exception {
        assertNull(convter.convert(null));
    }

    @Test
    void testEmptyObject() throws Exception {
        assertNotNull(convter.convert(new Category()));
    }

    @Test
    void convert() throws Exception {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = convter.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());

    }
}