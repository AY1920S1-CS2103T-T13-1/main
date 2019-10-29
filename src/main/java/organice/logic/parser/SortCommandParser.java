package organice.logic.parser;

import organice.logic.commands.SortCommand;
import organice.logic.parser.exceptions.ParseException;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String ORGAN_EXPIRY_DATE = "expiry";
    public static final String PRIORITY = "priority";
    public static final String SUCCESS_RATE = "rate";
    public static final String MESSAGE_INVALID_INPUTS = "The input should be 'expiry', 'priority' or 'rate'.";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String userInput) throws ParseException {
        if (!(userInput.equalsIgnoreCase(ORGAN_EXPIRY_DATE)
                || userInput.equalsIgnoreCase(PRIORITY)|| userInput.equalsIgnoreCase(SUCCESS_RATE))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(userInput);
    }
}
