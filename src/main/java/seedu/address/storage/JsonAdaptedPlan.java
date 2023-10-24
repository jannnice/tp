package seedu.address.storage;

import static seedu.address.logic.Messages.MESSAGE_PERSON_DOES_NOT_EXIST;

import java.lang.IllegalArgumentException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.plan.Plan;
import seedu.address.model.plan.PlanName;
import seedu.address.model.plan.PlanDateTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Plan}.
 */
class JsonAdaptedPlan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Plan's %s field is missing!";

    private final String planName;
    private final String planDateTime;
    private final String friend;

    /**
     * Constructs a {@code JsonAdaptedPlan} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPlan(@JsonProperty("planName") String planName, @JsonProperty("planDateTime") String planDateTime,
                             @JsonProperty("friend") String friend) {
        this.planName = planName;
        this.planDateTime = planDateTime;
        this.friend = friend;
    }

    /**
     * Converts a given {@code Plan} into this class for Jackson use.
     */
    public JsonAdaptedPlan(Plan source) {
        planName = source.getPlanName().toString();
        planDateTime = source.getPlanDateTime().toString();
        friend = source.getPlanFriend().getName().fullName;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Plan} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Plan toModelType(AddressBook addressBook) throws IllegalValueException {
        if (friend == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(friend)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        if (!addressBook.hasPerson(new Name(friend))) {
            throw new IllegalValueException(MESSAGE_PERSON_DOES_NOT_EXIST);
        }
        final Person modelPlanFriend = addressBook
                .getPersonList().stream().filter(person -> person.getName().toString().equals(friend))
                .collect(Collectors.toList()).get(0);

        if (planName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PlanName.class.getSimpleName()));
        }
        if (!PlanName.isValidPlanName(planName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final PlanName modelPlanName = new PlanName(planName);
        PlanDateTime modelPlanDateTime;
        if(planDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PlanDateTime.class.getSimpleName()));
        }
        try {
            modelPlanDateTime = new PlanDateTime(planDateTime);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(PlanDateTime.MESSAGE_CONSTRAINTS);
        }
        return new Plan(modelPlanName, modelPlanDateTime, modelPlanFriend);
    }

}
