package organice.logic.parser;

import static java.util.Objects.requireNonNull;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_RESULT;

import organice.logic.commands.DoneCommand;
import organice.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        String prefixNricString = PREFIX_NRIC.toString();
        String preficResString = PREFIX_RESULT.toString();

        String firstNric;
        String secondNric;

        String result;
        String[] nameKeywords = trimmedArgs.split("\\s+");

        try {
            if (nameKeywords[0].startsWith(prefixNricString) && nameKeywords[1].startsWith(prefixNricString)) {
                firstNric = nameKeywords[0].replaceFirst(prefixNricString, "");
                secondNric = nameKeywords[1].replaceFirst(prefixNricString, "");
                result = nameKeywords[2].replaceFirst(preficResString, "");
                if (result.trim().isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
                }
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
            }
            return new DoneCommand(firstNric, secondNric, result);
        } catch (NullPointerException npe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
    }
}
