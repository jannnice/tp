package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPlanAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PLAN;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PLAN;
import static seedu.address.testutil.TypicalPlans.getTypicalAddressBookWithPlans;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.plan.Plan;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UncompletePlanCommand}.
 */
public class UncompletePlanCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithPlans(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Plan planToUncomplete = model.getFilteredPlanList().get(INDEX_FIRST_PLAN.getZeroBased());
        UncompletePlanCommand uncompletePlanCommand = new UncompletePlanCommand(INDEX_FIRST_PLAN);

        String expectedMessage = String.format(UncompletePlanCommand.MESSAGE_UNCOMPLETE_PLAN_SUCCESS,
                planToUncomplete.getPlanName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.completePlan(planToUncomplete);

        assertCommandSuccess(uncompletePlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlanList().size() + 1);
        UncompletePlanCommand uncompletePlanCommand = new UncompletePlanCommand(outOfBoundIndex);

        assertCommandFailure(uncompletePlanCommand, model, Messages.MESSAGE_OUT_OF_BOUND_PLAN_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPlanAtIndex(model, INDEX_FIRST_PLAN);

        Index outOfBoundIndex = INDEX_SECOND_PLAN;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPlanList().size());

        UncompletePlanCommand uncompletePlanCommand = new UncompletePlanCommand(outOfBoundIndex);

        assertCommandFailure(uncompletePlanCommand, model, Messages.MESSAGE_OUT_OF_BOUND_PLAN_INDEX);
    }

    @Test
    public void equals() {
        UncompletePlanCommand uncompletePlanFirstCommand = new UncompletePlanCommand(INDEX_FIRST_PLAN);
        UncompletePlanCommand uncompletePlanSecondCommand = new UncompletePlanCommand(INDEX_SECOND_PLAN);

        // same object -> returns true
        assertTrue(uncompletePlanFirstCommand.equals(uncompletePlanFirstCommand));

        // same values -> returns true
        UncompletePlanCommand uncompletePlanFirstCommandCopy = new UncompletePlanCommand(INDEX_FIRST_PLAN);
        assertTrue(uncompletePlanFirstCommand.equals(uncompletePlanFirstCommandCopy));

        // different types -> returns false
        assertFalse(uncompletePlanFirstCommand.equals(1));

        // null -> returns false
        assertFalse(uncompletePlanFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(uncompletePlanFirstCommand.equals(uncompletePlanSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UncompletePlanCommand uncompletePlanCommand = new UncompletePlanCommand(targetIndex);
        String expected = UncompletePlanCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, uncompletePlanCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPlan(Model model) {
        model.updateFilteredPlanList(p -> false);

        assertTrue(model.getFilteredPlanList().isEmpty());
    }
}
