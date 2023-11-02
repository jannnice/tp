package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PLANS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.plan.Plan;
import seedu.address.model.plan.PlanDateTime;
import seedu.address.model.plan.PlanName;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditPlanCommand extends Command {

    public static final String COMMAND_WORD = "edit-plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the plan identified "
            + "by the index number used in the displayed plan list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATETIME + "DATE_TIME] "
            + "[" + PREFIX_FRIEND + "FRIEND_NAME]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATETIME + "2023-10-20-09:00 "
            + PREFIX_FRIEND + "John Doe";

    public static final String MESSAGE_EDIT_PLAN_SUCCESS = "Edited Plan: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PLAN = "This plan already exists in the FriendBook.";
    public static final String MESSAGE_FRIEND_NOT_FOUND = "The friend does not exist in the FriendBook";
    private final Index index;
    private final EditPlanDescriptor editPlanDescriptor;

    /**
     * @param index of the plan in the filtered plan list to edit
     * @param editPlanDescriptor details to edit the plan with
     */
    public EditPlanCommand(Index index, EditPlanDescriptor editPlanDescriptor) {
        requireNonNull(index);
        requireNonNull(editPlanDescriptor);

        this.index = index;
        this.editPlanDescriptor = new EditPlanDescriptor(editPlanDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownList = model.getFilteredPlanList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
        }

        Plan planToEdit = lastShownList.get(index.getZeroBased());

        Name editedPlanFriendName = editPlanDescriptor.getPlanFriendName().orElse(null);
        Person editedPlanFriend;
        if (editedPlanFriendName != null) {
            try {
                editedPlanFriend = model.getPersonByName(editedPlanFriendName);

                editPlanDescriptor.setPlanFriend(editedPlanFriend);
            } catch (PersonNotFoundException e) {
                throw new CommandException(MESSAGE_FRIEND_NOT_FOUND);
            }
        }

        Plan editedPlan = createEditedPlan(planToEdit, editPlanDescriptor);

        if (!planToEdit.isSamePlan(editedPlan) && model.hasPlan(editedPlan)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLAN);
        }

        model.setPlan(planToEdit, editedPlan);
        model.updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);
        return new CommandResult(String.format(MESSAGE_EDIT_PLAN_SUCCESS, Messages.format(editedPlan)));
    }

    /**
     * Creates and returns a {@code Plan} with the details of {@code planToEdit}
     * edited with {@code editPlanDescriptor}.
     */
    private static Plan createEditedPlan(Plan planToEdit, EditPlanDescriptor editPlanDescriptor) {
        assert planToEdit != null;

        PlanName updatedName = editPlanDescriptor.getPlanName().orElse(planToEdit.getPlanName());
        PlanDateTime updatedDateTime = editPlanDescriptor.getPlanDateTime().orElse(planToEdit.getPlanDateTime());
        Person updatedFriend = editPlanDescriptor.getPlanFriend().orElse(planToEdit.getPlanFriend());

        return new Plan(updatedName, updatedDateTime, updatedFriend);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPlanCommand)) {
            return false;
        }

        EditPlanCommand otherEditPlanCommand = (EditPlanCommand) other;
        return index.equals(otherEditPlanCommand.index)
                && editPlanDescriptor.equals(otherEditPlanCommand.editPlanDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPlanDescriptor", editPlanDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the plan with. Each non-empty field value will replace the
     * corresponding field value of the plan.
     */
    public static class EditPlanDescriptor {
        private PlanName planName;
        private PlanDateTime dateTime;
        private Name friendName;
        private Person friend;

        public EditPlanDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPlanDescriptor(EditPlanDescriptor toCopy) {
            setPlanName(toCopy.planName);
            setPlanDateTime(toCopy.dateTime);
            setPlanFriendName(toCopy.friendName);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(planName, dateTime, friendName);
        }

        public void setPlanName(PlanName planName) {
            this.planName = planName;
        }

        public Optional<PlanName> getPlanName() {
            return Optional.ofNullable(planName);
        }

        public void setPlanDateTime(PlanDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<PlanDateTime> getPlanDateTime() {
            return Optional.ofNullable(dateTime);
        }

        public void setPlanFriendName(Name friendName) {
            this.friendName = friendName;
        }

        public Optional<Name> getPlanFriendName() {
            return Optional.ofNullable(friendName);
        }

        public void setPlanFriend(Person friend) {
            this.friend = friend;
        }

        public Optional<Person> getPlanFriend() {
            return Optional.ofNullable(friend);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPlanDescriptor)) {
                return false;
            }

            EditPlanDescriptor otherEditPlanDescriptor = (EditPlanDescriptor) other;
            return Objects.equals(planName, otherEditPlanDescriptor.planName)
                    && Objects.equals(dateTime, otherEditPlanDescriptor.dateTime)
                    && Objects.equals(friendName, otherEditPlanDescriptor.friendName);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("plan name", planName)
                    .add("plan dateTime", dateTime)
                    .add("friend", friendName)
                    .toString();
        }
    }
}
