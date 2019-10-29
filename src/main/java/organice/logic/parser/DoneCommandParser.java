package organice.logic.parser;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;

import organice.logic.commands.DoneCommand;
import organice.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProcessingCommand
     * and returns a ProcessingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String PREFIX_NRIC_STRING = PREFIX_NRIC.toString();

        String firstNRIC;
        String secondNRIC;
        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (nameKeywords[0].startsWith(PREFIX_NRIC_STRING) && nameKeywords[1].startsWith(PREFIX_NRIC_STRING)) {
            firstNRIC = nameKeywords[0].replaceFirst(PREFIX_NRIC_STRING, "");
            secondNRIC = nameKeywords[1].replaceFirst(PREFIX_NRIC_STRING, "");
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
        return new DoneCommand(firstNRIC, secondNRIC);
    }
}
