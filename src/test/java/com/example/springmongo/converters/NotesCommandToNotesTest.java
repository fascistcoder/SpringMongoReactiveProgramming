package com.example.springmongo.converters;

import com.example.springmongo.commands.NotesCommand;
import com.example.springmongo.model.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 27/09/21
 */
class NotesCommandToNotesTest {

	public static final String ID_VALUE = "1";
	public static final String RECIPE_NOTES = "Notes";
	NotesCommandToNotes converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new NotesCommandToNotes();

	}

	@Test
	void testNullParameter() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new NotesCommand()));
	}

	@Test
	void convert() throws Exception {
		//given
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(ID_VALUE);
		notesCommand.setRecipeNotes(RECIPE_NOTES);

		//when
		Notes notes = converter.convert(notesCommand);

		//then
		assertNotNull(notes);
		assertEquals(ID_VALUE, notes.getId());
		assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
	}
}