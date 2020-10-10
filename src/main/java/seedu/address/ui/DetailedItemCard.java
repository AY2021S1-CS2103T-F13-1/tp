package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.item.Item;


/**
 * An UI component that displays information of a {@code Item}.
 */
public class DetailedItemCard extends UiPart<Region> {

    private static final String FXML = "InventoryListDetailedCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Item item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label quantity;
    @FXML
    private Label description;

    /**
     * Creates a {@code ItemCode} with the given {@code Item} and index to display.
     */
    public DetailedItemCard(Item item, int displayedIndex) {
        super(FXML);
        this.item = item;
        name.setText(item.getName());
        quantity.setText("Quantity: " + item.getQuantity());
        description.setText(item.getDescription());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailedItemCard)) {
            return false;
        }

        // state check
        DetailedItemCard card = (DetailedItemCard) other;
        return item.equals(card.item);
    }
}
