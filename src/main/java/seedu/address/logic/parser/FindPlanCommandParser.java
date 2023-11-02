package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_MISSING_ARGUMENTS;

import seedu.address.logic.commands.FindPlanCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new FindPlanCommand object
 */
public class FindPlanCommandParser implements Parser<FindPlanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPlanCommand
     * and returns a FindPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPlanCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_MISSING_ARGUMENTS, FindPlanCommand.MESSAGE_USAGE));
        }

        return new FindPlanCommand(new Name(trimmedArgs));
    }
}
