package organice.logic.parser;

import static org.junit.jupiter.api.Assertions.*;
import static organice.logic.parser.CommandParserTestUtil.assertParseFailure;
import static organice.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import organice.logic.commands.ListCommand;
import organice.model.person.Type;

class DoneCommandParserTest {

    private static final Type TYPE_PATIENT = new Type("patient");
    private static final Type TYPE_DONOR = new Type("donor");
    private static final Type TYPE_DOCTOR = new Type("doctor");

    @Test
    public void parse_validArgs_returnsDoneCommand() {
    }
    @Test
    void parse() {
    }
}
