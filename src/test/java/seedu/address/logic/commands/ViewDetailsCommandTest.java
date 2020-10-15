package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.item.Item;
import seedu.address.model.item.NameIsExactlyPredicate;
import seedu.address.testutil.ItemBuilder;
import seedu.address.ui.DisplayedInventoryType;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewDetailsCommand}.
 */
public class ViewDetailsCommandTest {
    private ModelStubWithItem modelStub;
    private Item validItem;

    @BeforeEach
    public void setUp() {
        validItem = new ItemBuilder().build();
        modelStub = new ModelStubWithItem(validItem);
    }

    @Test
    public void equals() {
        NameIsExactlyPredicate firstPredicate =
                new NameIsExactlyPredicate(Collections.singletonList("first"));
        NameIsExactlyPredicate secondPredicate =
                new NameIsExactlyPredicate(Collections.singletonList("second"));

        ViewDetailsCommand findFirstCommand = new ViewDetailsCommand(firstPredicate);
        ViewDetailsCommand findSecondCommand = new ViewDetailsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ViewDetailsCommand findFirstCommandCopy = new ViewDetailsCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_filterItem_success() {
        String expectedMessage = "Displaying searched item";
        NameIsExactlyPredicate predicate = new NameIsExactlyPredicate(
                Collections.singletonList("Bob's Iridescent Grape"));
        ViewDetailsCommand command = new ViewDetailsCommand(predicate);
        assertEquals(command.execute(modelStub), new CommandResult(expectedMessage, false,
                false, DisplayedInventoryType.DETAILED_ITEM));
        List<Item> expectedList = Collections.singletonList(validItem);
        ObservableList<Item> expectedObservableList = FXCollections.observableList(expectedList);
        FilteredList<Item> expectedFilteredList = expectedObservableList.filtered(predicate);
        assertEquals(expectedFilteredList, modelStub.getFilteredItemList());
    }

    /**
     * A Model stub that contains a single item.
     */
    private class ModelStubWithItem extends ModelStub {
        private final Item item;

        ModelStubWithItem(Item item) {
            requireNonNull(item);
            this.item = item;
        }

        @Override
        public void updateFilteredItemList(Predicate<Item> predicate) {
            // do nothing
        }

        @Override
        public ObservableList<Item> getFilteredItemList() {
            List<Item> list = Collections.singletonList(item);
            return FXCollections.observableList(list);
        }
    }
}
