package organice.logic.parser;

import static organice.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import organice.logic.commands.ListCommand;
import organice.model.person.Type;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {

        // no leading and trailing whitespaces -- no argument given
        ListCommand expectedListCommand = new ListCommand(null);
        assertParseSuccess(parser, "list", expectedListCommand);

        // no leading and trailing whitespaces -- patient
        expectedListCommand = new ListCommand(new Type("patient"));
        assertParseSuccess(parser, "list t/patient", expectedListCommand);

        // no leading and trailing whitespaces -- donor
        expectedListCommand = new ListCommand(new Type("donor"));
        assertParseSuccess(parser, "list t/donor", expectedListCommand);

        // no leading and trailing whitespaces -- doctor
        expectedListCommand = new ListCommand(new Type("doctor"));
        assertParseSuccess(parser, "list t/doctor", expectedListCommand);

        // leading and trailing whitespaces -- no argument given
        expectedListCommand = new ListCommand(null);
        assertParseSuccess(parser, "list   ", expectedListCommand);

        // leading and trailing whitespaces -- patient
        expectedListCommand = new ListCommand(new Type("patient"));
        assertParseSuccess(parser, "list    t/patient", expectedListCommand);

        // leading and trailing whitespaces -- donor
        expectedListCommand = new ListCommand(new Type("donor"));
        assertParseSuccess(parser, "list    t/donor", expectedListCommand);

        // leading and trailing whitespaces -- doctor
        expectedListCommand = new ListCommand(new Type("doctor"));
        assertParseSuccess(parser, "list      t/doctor", expectedListCommand);
    }

}
