package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PLANS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.plan.Plan;

/**
 * Completes a plan identified using it's displayed index from the FriendBook.
 */
public class UncompletePlanCommand extends Command {

    public static final String COMMAND_WORD = "uncomplete-plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the plan as not completed identified by the index number used in the displayed plan list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNCOMPLETE_PLAN_SUCCESS = "\"%1$s\" is marked as not completed";

    private final Index targetIndex;

    public UncompletePlanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownList = model.getFilteredPlanList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_OUT_OF_BOUND_PLAN_INDEX);
        }

        Plan planToUncomplete = lastShownList.get(targetIndex.getZeroBased());
        model.uncompletePlan(planToUncomplete);
        model.updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);
        return new CommandResult(String.format(MESSAGE_UNCOMPLETE_PLAN_SUCCESS, planToUncomplete.getPlanName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UncompletePlanCommand)) {
            return false;
        }

        UncompletePlanCommand otherUncompletePlanCommand = (UncompletePlanCommand) other;
        return targetIndex.equals(otherUncompletePlanCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
