package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalItems.*;
import static seedu.address.testutil.TypicalRecipes.getTypicalRecipeList;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.model.item.Item;
import seedu.address.model.recipe.Recipe;

public class DeleteItemCommandTest {
    private ModelStubWithItemAndRecipeList modelStub;
    private ModelStubWithItemAndRecipeList expectedModelStub;

    @BeforeEach
    public void setUp() {
        modelStub = new ModelStubWithItemAndRecipeList(getTypicalItemList(), getTypicalRecipeList());
        expectedModelStub = new ModelStubWithItemAndRecipeList(getTypicalItemList(), getTypicalRecipeList());
    }

    @Test
    public void constructor_throwsNullException() {
        assertThrows(NullPointerException.class, () -> new DeleteItemCommand(null));
    }

    /**
     * Tests for successful deletion of a item found in the item list
     * with no connecting recipes
     */
    @Test
    public void execute_isolatedItemDeletion_success() {
        DeleteItemCommand dic = new DeleteItemCommand(PEAR.getName());
        expectedModelStub.deleteItem(PEAR);
        String expectedMessage = String.format(DeleteItemCommand.MESSAGE_SUCCESS, PEAR);
        assertCommandSuccess(dic, modelStub, expectedMessage, expectedModelStub);
    }

    @Test
    public void execute_itemNotFound_throwsCommandException() {
        DeleteItemCommand dic = new DeleteItemCommand("Bob's Toe nail");
        assertThrows(CommandException.class, DeleteItemCommand.MESSAGE_ITEM_NOT_FOUND, (
            ) -> dic.execute(modelStub));
        assertEquals(modelStub, expectedModelStub);
    }

    /**
     * Tests for successful deletion of a item found in the item list
     * with recipes
     */
    @Test
    public void execute_itemDeletion_success() {
        Item itemToDelete = APPLE;
        DeleteItemCommand deleteItemCommand = new DeleteItemCommand(itemToDelete.getName());
        String expectedMessage = String.format(DeleteItemCommand.MESSAGE_SUCCESS, itemToDelete);

        // Perform a manual deletion of all matching recipes
        expectedModelStub.deleteItem(itemToDelete);
        List<Recipe> recipeList = new ArrayList<>(expectedModelStub.getFilteredRecipeList());
        recipeList.removeIf(y -> y.isDeleted()
                || !y.getProductName().equals(itemToDelete.getName())
                && y.getIngredients()
                .asUnmodifiableObservableList()
                .stream()
                .noneMatch(z -> z.isItem(itemToDelete.getId())));

        recipeList.forEach(expectedModelStub::deleteRecipe);

        assertCommandSuccess(deleteItemCommand, modelStub, expectedMessage, expectedModelStub);
    }

    @Test
    public void execute_itemIsSoftDeleted() throws Exception {
        DeleteItemCommand dic = new DeleteItemCommand(PEAR.getName());
        dic.execute(modelStub);

        //Assert that the item contained still exists within the model
        assertTrue(modelStub.getItemList().getItemList().contains(PEAR.delete()));
    }

    /**
     * A Model stub which contains a item and a recipe list.
     */
    private class ModelStubWithItemAndRecipeList extends ModelStub {
        private final ItemList itemList;
        private final FilteredList<Item> filteredItems;
        private final RecipeList recipeList;
        private final FilteredList<Recipe> filteredRecipes;

        public ModelStubWithItemAndRecipeList(ReadOnlyItemList itemList, ReadOnlyRecipeList recipeList) {
            this.recipeList = new RecipeList(recipeList);
            filteredRecipes = new FilteredList<>(this.recipeList.getRecipeList());
            this.itemList = new ItemList(itemList);
            filteredItems = new FilteredList<>(this.itemList.getItemList());
        }

        @Override
        public ReadOnlyRecipeList getRecipeList() {
            return recipeList;
        }

        @Override
        public ObservableList<Recipe> getFilteredRecipeList() {
            return filteredRecipes;
        }

        @Override
        public void deleteRecipe(Recipe target) {
            recipeList.deleteRecipe(target);
        }

        @Override
        public ReadOnlyItemList getItemList() {
            return itemList;
        }

        @Override
        public ObservableList<Item> getFilteredItemList() {
            return filteredItems;
        }

        @Override
        public void deleteItem(Item item) {
            itemList.deleteItem(item);
        }
    }
}
