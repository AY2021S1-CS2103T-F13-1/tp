package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.TagMatchesKeywordsPredicate;

public class FindByTagCommandParser implements Parser<FindByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindByTagCommand
     * and returns a FindByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindByTagCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindByTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        return new FindByTagCommand(new TagMatchesKeywordsPredicate(Arrays.asList(tagKeywords)));
    }
}