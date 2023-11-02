package seedu.address.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the date and time of a Plan.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class PlanDateTime {
    public static final String MESSAGE_INVALID_DATETIME = "Date-Time given is invalid.\n";

    public static final String MESSAGE_CONSTRAINTS = MESSAGE_INVALID_DATETIME + " "
            + "Date-Time must be in YYYY-MM-DD-HH:MM format";

    public static final String FUTURE_MESSAGE_CONSTRAINT = MESSAGE_INVALID_DATETIME
            + "Ensure that the Date-Time provided is not in the past.";

    // public static final String VALIDATION_REGEX = "(202[3-9]|20[3-9][0-9]|21[0-9]{2})-(0[1-9]|1[0-2])-"
    public static final String VALIDATION_REGEX = "[0-9][0-9][0-9][0-9]-(0[1-9]|1[0-2])-"
            + "(0[1-9]|[1-2][0-9]|3[0-1])-(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]";

    public final LocalDateTime planDateTime;

    /**
     * Constructs a {@code PlanDateTime}.
     *
     * @param dateTimeString A valid date time string of the format YYYY-MM-DD-HH:MM.
     */
    public PlanDateTime(String dateTimeString) {
        requireNonNull(dateTimeString);
        checkArgument(isValidDateTime(dateTimeString), MESSAGE_CONSTRAINTS);
        checkArgument(isFutureDateTime(dateTimeString), FUTURE_MESSAGE_CONSTRAINT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        planDateTime = dateTime;
    }

    /**
     * Returns true if a given date time is valid.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given date time is in the future.
     */
    public static boolean isFutureDateTime(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(test, formatter);
        } catch (DateTimeParseException e) {
            // this exception is thrown if year is 0000
            return false;
        }
        LocalDateTime current = LocalDateTime.now();
        return dateTime.isAfter(current);
    }

    @Override
    public String toString() {
        String dateTimeString = planDateTime.toString();
        String formattedString = dateTimeString.replace('T', ' ');
        return formattedString;
    }

    /**
     * Returns a string representation of the plan's date and time in a raw format.
     * The date and time are combined into a single string, and the 'T' separator is replaced with a hyphen ('-').
     *
     * @return A raw string representation of the plan's date and time.
     */
    public String toStringRaw() {
        String dateTimeString = planDateTime.toString();
        String formattedString = dateTimeString.replace('T', '-');
        return formattedString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PlanDateTime)) {
            return false;
        }

        PlanDateTime otherTime = (PlanDateTime) other;
        return this.planDateTime.equals(otherTime.planDateTime);
    }

    @Override
    public int hashCode() {
        return this.planDateTime.hashCode();
    }
}
