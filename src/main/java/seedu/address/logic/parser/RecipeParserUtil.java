package seedu.address.logic.parser;

import javafx.util.Pair;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.Ingredient;
import seedu.address.model.recipe.IngredientList;
import seedu.address.model.recipe.IngredientPrecursor;
import seedu.address.model.recipe.ProductQuantity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static java.util.Objects.requireNonNull;

public class RecipeParserUtil {

    private static final Pattern SPLIT_INGREDIENT_FORMAT = Pattern.compile("[^\\[]+\\[\\d+");
    private static final String MESSAGE_INGREDIENT_FORMAT = "Ingredients should be listed as itemName[qty], ...";

    public static String parseProductName(String productName) {
        requireNonNull(productName);
        return productName.trim();
    }

    public static List<IngredientPrecursor> parseIngredients(String ingredients) throws ParseException {
        String[] splitIngredients = ingredients.split("],|]");
        if (splitIngredients.length < 1 || !RecipeParserUtil.checkIngredients(splitIngredients)) {
            throw new ParseException(MESSAGE_INGREDIENT_FORMAT);
        }

        List<IngredientPrecursor> ingredientPrecursors = new ArrayList<>();
        String[] splitParts;
        for (String ingredient : splitIngredients) {
            splitParts = ingredient.trim().split("\\[");
            ingredientPrecursors.add(new IngredientPrecursor(splitParts[0], Integer.parseInt(splitParts[1])));
        }
        return ingredientPrecursors;
    }

    private static boolean checkIngredients(String[] splitIngredients) {
        for (String ingredient : splitIngredients) {
            Matcher matcher = SPLIT_INGREDIENT_FORMAT.matcher(ingredient);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    public static ProductQuantity parseProductQuantity(String productQuantity) throws ParseException {
        requireNonNull(productQuantity);
        String trimmedQuantity = productQuantity.trim();
        if (!ProductQuantity.isValidQuantity(trimmedQuantity)) {
            throw new ParseException(ProductQuantity.MESSAGE_CONSTRAINTS);
        }
        return new ProductQuantity(trimmedQuantity);
    }

    public static String parseDescription(String description) {
        requireNonNull(description);
        return description.trim();
    }
}
