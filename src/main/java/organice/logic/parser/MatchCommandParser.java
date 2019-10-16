package organice.logic.parser;

import static java.util.Objects.requireNonNull;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;

import organice.logic.commands.MatchCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Nric;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class MatchCommandParser implements Parser<MatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        //TODO: Add prefix for all/
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC);
        String output;

        //TODO: Add testing for all/
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            output = nric.toString();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        return new MatchCommand(output);
    }

}
