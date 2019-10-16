package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import organice.logic.commands.ListCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Type;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Returns the {@code Type} of person in the given {@code ArgumentMultimap}
     * @throws ParseException if the type is not specified correctly in the input arguments
     */
    private static Type parseType(ArgumentMultimap argumentMultimap) throws ParseException {
        try {
            return ParserUtil.parseType(argumentMultimap.getValue(PREFIX_TYPE).get());
        } catch (NoSuchElementException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE);
        Type type = null;
        if (isTypePresent(argMultimap, PREFIX_TYPE)) {
            type = parseType(argMultimap);
            return new ListCommand(type);
        }
        return new ListCommand(type); // if no type specified return list of all persons
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isTypePresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
