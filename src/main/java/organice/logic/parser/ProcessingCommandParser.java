package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.Arrays;

import organice.logic.commands.ProcessingCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.NameContainsKeywordsPredicate;
import organice.model.person.Nric;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ProcessingCommandParser implements Parser<ProcessingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProcessingCommand
     * and returns a ProcessingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ProcessingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String firstNRIC;
        String secondNRIC;
        String[] nameKeywords = trimmedArgs.split("\\s+");
        if (nameKeywords[1].startsWith("icP/") && nameKeywords[2].startsWith("icD/")
            || nameKeywords[1].startsWith("icD/") && nameKeywords[2].startsWith("icP/")) {
            firstNRIC = nameKeywords[1];
            secondNRIC = nameKeywords[2];
        } else if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProcessingCommand.MESSAGE_USAGE));
        }
        return new ProcessingCommand(firstNRIC, secondNRIC);
    }

}
