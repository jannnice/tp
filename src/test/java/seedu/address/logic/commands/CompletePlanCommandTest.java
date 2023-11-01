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
 * {@code CompletePlanCommand}.
 */
public class CompletePlanCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithPlans(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Plan planToComplete = model.getFilteredPlanList().get(INDEX_FIRST_PLAN.getZeroBased());
        CompletePlanCommand completePlanCommand = new CompletePlanCommand(INDEX_FIRST_PLAN);

        String expectedMessage = String.format(CompletePlanCommand.MESSAGE_COMPLETE_PLAN_SUCCESS,
                planToComplete.getPlanName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.completePlan(planToComplete);

        assertCommandSuccess(completePlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlanList().size() + 1);
        CompletePlanCommand completePlanCommand = new CompletePlanCommand(outOfBoundIndex);

        assertCommandFailure(completePlanCommand, model, Messages.MESSAGE_OUT_OF_BOUND_PLAN_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPlanAtIndex(model, INDEX_FIRST_PLAN);

        Index outOfBoundIndex = INDEX_SECOND_PLAN;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPlanList().size());

        CompletePlanCommand completePlanCommand = new CompletePlanCommand(outOfBoundIndex);

        assertCommandFailure(completePlanCommand, model, Messages.MESSAGE_OUT_OF_BOUND_PLAN_INDEX);
    }

    @Test
    public void equals() {
        CompletePlanCommand completePlanFirstCommand = new CompletePlanCommand(INDEX_FIRST_PLAN);
        CompletePlanCommand completePlanSecondCommand = new CompletePlanCommand(INDEX_SECOND_PLAN);

        // same object -> returns true
        assertTrue(completePlanFirstCommand.equals(completePlanFirstCommand));

        // same values -> returns true
        CompletePlanCommand completePlanFirstCommandCopy = new CompletePlanCommand(INDEX_FIRST_PLAN);
        assertTrue(completePlanFirstCommand.equals(completePlanFirstCommandCopy));

        // different types -> returns false
        assertFalse(completePlanFirstCommand.equals(1));

        // null -> returns false
        assertFalse(completePlanFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(completePlanFirstCommand.equals(completePlanSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CompletePlanCommand completePlanCommand = new CompletePlanCommand(targetIndex);
        String expected = CompletePlanCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, completePlanCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no plan.
     */
    private void showNoPlan(Model model) {
        model.updateFilteredPlanList(p -> false);

        assertTrue(model.getFilteredPlanList().isEmpty());
    }
}
