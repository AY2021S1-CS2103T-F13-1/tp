package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.model.*;
import seedu.address.model.item.NameMatchesKeywordsPredicate;
import seedu.address.testutil.TypicalItems;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalItems.getTypicalItemList;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

/**
 * Contains integration tests (interaction with the Model) for {@code FindItemCommand}.
 */
public class FindItemCommandTest {
    // TODO update tests after command implementation.

    private Model model = new ModelManager(getTypicalItemList(), new LocationList(),
            new RecipeList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalItemList(), new LocationList(),
            new RecipeList(), new UserPrefs());

    @Test
    public void equals() {
        NameMatchesKeywordsPredicate firstPredicate =
                new NameMatchesKeywordsPredicate(Collections.singletonList("first"));
        NameMatchesKeywordsPredicate secondPredicate =
                new NameMatchesKeywordsPredicate(Collections.singletonList("second"));

        FindItemCommand findFirstCommand = new FindItemCommand(firstPredicate);
        FindItemCommand findSecondCommand = new FindItemCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindItemCommand findFirstCommandCopy = new FindItemCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_NO_ITEM_MATCH);

        NameMatchesKeywordsPredicate predicate = preparePredicate(" ");
        FindItemCommand command = new FindItemCommand(predicate);

        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredItemList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, 2);

        NameMatchesKeywordsPredicate predicate = preparePredicate("Apple Banana Carrot");
        FindItemCommand command = new FindItemCommand(predicate);

        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalItems.APPLE, TypicalItems.BANANA), model.getFilteredItemList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameMatchesKeywordsPredicate preparePredicate(String userInput) {
        return new NameMatchesKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
