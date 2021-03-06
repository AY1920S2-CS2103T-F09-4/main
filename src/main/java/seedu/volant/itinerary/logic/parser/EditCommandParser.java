package seedu.volant.itinerary.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.volant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.volant.commons.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.volant.commons.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.volant.commons.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.volant.commons.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.volant.commons.core.index.Index;
import seedu.volant.commons.logic.parser.ArgumentMultimap;
import seedu.volant.commons.logic.parser.ArgumentTokenizer;
import seedu.volant.commons.logic.parser.Parser;
import seedu.volant.commons.logic.parser.ParserUtil;
import seedu.volant.commons.logic.parser.exceptions.ParseException;
import seedu.volant.itinerary.logic.commands.EditCommand;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditItineraryDescriptor editItineraryDescriptor = new EditCommand.EditItineraryDescriptor();
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editItineraryDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editItineraryDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editItineraryDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editItineraryDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }

        if (!editItineraryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editItineraryDescriptor);
    }
}
