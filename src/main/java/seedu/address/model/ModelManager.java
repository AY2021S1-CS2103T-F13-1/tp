package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.item.Item;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.recipe.Recipe;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final ItemList itemList;
    private final FilteredList<Item> filteredItems;
    private final LocationList locationList;
    private final FilteredList<Location> filteredLocations;
    private final RecipeList recipeList;
    private final FilteredList<Recipe> filteredRecipes;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        itemList = null;
        filteredItems = null;
        locationList = null;
        filteredLocations = null;
        recipeList = null;
        filteredRecipes = null;
    }

    /**
     * Initializes a ModelManager with the given itemList and userPrefs.
     */
    public ModelManager(ReadOnlyItemList itemList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(itemList, userPrefs);

        logger.fine("Initializing with item list: " + itemList + " and user prefs " + userPrefs);

        addressBook = null;
        this.userPrefs = new UserPrefs(userPrefs);
        this.itemList = new ItemList(itemList);
        filteredItems = new FilteredList<>(this.itemList.getItemList());
        filteredPersons = null;
        locationList = null;
        filteredLocations = null;
        recipeList = null;
        filteredRecipes = null;
    }

    /**
     * Initializes a ModelManager with the given locationList and userPrefs.
     */
    public ModelManager(ReadOnlyLocationList locationList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(locationList, userPrefs);

        logger.fine("Initializing with location list: " + locationList + " and user prefs " + userPrefs);

        addressBook = null;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = null;
        itemList = null;
        filteredItems = null;
        this.locationList = new LocationList(locationList);
        filteredLocations = new FilteredList<>(this.locationList.getLocationList());
        recipeList = null;
        filteredRecipes = null;
    }

    /**
     * Initializes a ModelManager with the given recipeList and userPrefs.
     */
    public ModelManager(ReadOnlyRecipeList recipeList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(recipeList, userPrefs);

        logger.fine("Initializing with recipe list: " + recipeList + " and user prefs " + userPrefs);

        addressBook = null;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = null;
        itemList = null;
        filteredItems = null;
        locationList = null;
        filteredLocations = null;
        this.recipeList = new RecipeList(recipeList);
        filteredRecipes = new FilteredList<>(this.recipeList.getRecipeList());;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public Path getItemListFilePath() {
        return userPrefs.getItemListFilePath();
    }

    @Override
    public Path getLocationListFilePath() {
        return userPrefs.getLocationListFilePath();
    }

    @Override
    public Path getRecipeListFilePath() {
        return userPrefs.getRecipeListFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setItemListFilePath(Path itemListFilePath) {
        requireNonNull(itemListFilePath);
        userPrefs.setItemListFilePath(itemListFilePath);
    }

    @Override
    public void setRecipeListFilePath(Path recipeListFilePath) {
        requireNonNull(recipeListFilePath);
        userPrefs.setRecipeListFilePath(recipeListFilePath);
    }

    //=========== Item and Location List =====================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public void setItemList(ReadOnlyItemList itemList) {
        this.itemList.resetData(itemList);
    }

    @Override
    public void setRecipeList(ReadOnlyRecipeList recipeList) {
        this.recipeList.resetData(recipeList);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyItemList getItemList() {
        return itemList;
    }

    @Override
    public ReadOnlyLocationList getLocationList() {
        return locationList;
    }

    @Override
    public ReadOnlyRecipeList getRecipeList() {
        return recipeList;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return itemList.hasItem(item);
    }

    @Override
    public boolean hasLocation(Location location) {
        requireNonNull(location);
        return locationList.hasLocation(location);
    }

    @Override
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipeList.hasRecipe(recipe);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void deleteItem(Item target) {
        itemList.removeItem(target);
    }

    @Override
    public void deleteRecipe(Recipe target) {
        recipeList.removeRecipe(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addItem(Item item) {
        itemList.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public void addLocation(Location location) {
        locationList.addLocation(location);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeList.addRecipe(recipe);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        itemList.setItem(target, editedItem);
    }

    @Override
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);

        recipeList.setRecipe(target, editedRecipe);
    }

    //=========== Filtered Item and Location List Accessors ==================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Item> getFilteredItemList() {
        return filteredItems;
    }

    @Override
    public ObservableList<Location> getFilteredLocationList() {
        return filteredLocations;
    }

    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return filteredRecipes;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    @Override
    public void updateFilteredLocationList(Predicate<Location> predicate) {
        requireNonNull(predicate);
        filteredLocations.setPredicate(predicate);
    }

    @Override
    public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
        requireNonNull(predicate);
        filteredRecipes.setPredicate(predicate);
    }

    @Override
    public int findLocationID(Location toFind) {
        requireNonNull(toFind);
        return locationList.findLocationID(toFind);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
