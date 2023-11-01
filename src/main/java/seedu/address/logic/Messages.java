package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.plan.Plan;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "Friend not in FriendBook";

    public static final String MESSAGE_UNKNOWN_COMMAND = "Invalid command. Here are all the valid commands:\n"
            + "Friends: add-friend, edit-friend, delete-friend, find-friend, list-friend\n"
            + "Plans: add-plan, edit-plan, complete-plan, uncomplete-plan, find-plan, list-plan\n"
            + "Others: clear, help, exit\n";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command. \n%1$s";
    public static final String MESSAGE_MISSING_ARGUMENTS = "Invalid syntax: Missing arguments.\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The friend index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d friends listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_UNCLEAR_COMMAND =
                "Command is unclear. Please specify with \"-friend\" or \"-plan\"!";
    public static final String MESSAGE_INVALID_PLAN_DISPLAYED_INDEX = "The plan index provided is invalid";
    public static final String MESSAGE_OUT_OF_BOUND_PLAN_INDEX = "No plans at given index.";

    public static final String MESSAGE_PERSON_PRESENT_IN_PLAN = "This person is involved in plans!";
    public static final String MESSAGE_PLANS_LISTED_OVERVIEW = "%1$d plans listed!";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(", ")
                .append(person.getPhone())
                .append(", ")
                .append(person.getEmail())
                .append(", ")
                .append(person.getAddress());
        return builder.toString();
    }

    /**
     * Formats the {@code plan} for display to the user.
     */
    public static String format(Plan plan) {
        final StringBuilder builder = new StringBuilder();
        builder.append(plan.getPlanName())
                .append(" with ")
                .append(plan.getPlanFriend().getName())
                .append(" at ")
                .append(plan.getPlanDateTime());
        return builder.toString();
    }

}
