package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ItemParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Quantity;

public class ItemParserUtilTest {

    private static final String INVALID_QUANTITY = "-1";
    private static final String VALID_NAME = "Apple";
    private static final String VALID_QUANTITY = "1";
    private static final String VALID_DESCRIPTION = "Red and cute.";
    private static final Collection<String> VALID_LOCATIONS = Set.of("City", "Town");
    private static final Collection<String> VALID_MULTI_LOCATIONS = Set.of("City, Singapore",
            "Town, Kerning City");
    private static final Collection<String> VALID_MULTI_LOCATION_PARSED = Set.of("City", "Town",
            "Singapore", "Kerning City");
    // Valid tag strings
    private static final String VALID_BERT = "bert model";
    private static final String VALID_TUTURU = "tuturu";
    private static final String VALID_ASD = "asd";
    private static final String VALID_ABC = "abc";
    //Valid tags
    /* These declarations causes Initialisation Errors for some reason
    private static final Tag TAG_BERT = new Tag(VALID_BERT);
    private static final Tag TAG_TUTURU = new Tag(VALID_TUTURU);
    private static final Tag TAG_ASD = new Tag(VALID_ASD);
    private static final Tag TAG_ABC = new Tag(VALID_ABC);
    private static final Collection<String> VALID_TAG_STRING = Set.of(VALID_ABC);
    private static final Collection<String> VALID_TAG_MULTI_STRING = Set.of("abc",
            "asd,tuturu , bert model");
    private static final Collection<Tag> VALID_TAG_SINGLE_PARSED = Set.of(TAG_ABC);
    private static final Collection<Tag> VALID_TAG_MULTI_PARSED = Set.of(TAG_ABC,
            TAG_ASD, TAG_BERT, TAG_TUTURU);*/

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ItemParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ItemParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_ITEM, ItemParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ITEM, ItemParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ItemParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() {
        assertEquals(VALID_NAME, ItemParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        assertEquals(VALID_NAME, ItemParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseQuantity_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ItemParserUtil.parseQuantity((String) null));
    }

    @Test
    public void parseQuantity_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ItemParserUtil.parseQuantity(INVALID_QUANTITY));
    }

    @Test
    public void parseQuantity_validValueWithoutWhitespace_returnsQuantity() throws Exception {
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ItemParserUtil.parseQuantity(VALID_QUANTITY));
    }

    @Test
    public void parseQuantity_validValueWithWhitespace_returnsTrimmedQuantity() throws Exception {
        String quantityWithWhitespace = WHITESPACE + VALID_QUANTITY + WHITESPACE;
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ItemParserUtil.parseQuantity(quantityWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ItemParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() {
        assertEquals(VALID_DESCRIPTION, ItemParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        assertEquals(VALID_DESCRIPTION, ItemParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseLocation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ItemParserUtil.parseLocations(null));
    }

    @Test
    public void parseLocation_validValue_returnsLocations() {
        assertEquals(VALID_LOCATIONS, ItemParserUtil.parseLocations(VALID_LOCATIONS));
    }

    @Test
    public void parseMultiLocation_validValue_returnFlatLocations() {
        assertEquals(VALID_MULTI_LOCATION_PARSED, ItemParserUtil.parseLocations(VALID_MULTI_LOCATIONS));
    }

    /*@Test
    public void parseTag_validValue_returnsTag() {
        assertEquals(VALID_TAG_SINGLE_PARSED, ItemParserUtil.parseTags(VALID_TAG_STRING));
    }

    @Test
    public void parseMultiTag_validValue_returnsTags() {
        assertEquals(VALID_TAG_MULTI_PARSED, ItemParserUtil.parseTags(VALID_TAG_MULTI_STRING));
    }*/
}
