package seedu.address.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.plan.exceptions.DuplicatePlanException;
import seedu.address.model.plan.exceptions.PlanNotFoundException;

/**
 * A list of plans that enforces uniqueness between its elements and does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class UniquePlanList implements Iterable<Plan> {

    private final ObservableList<Plan> internalList = FXCollections.observableArrayList();
    private final ObservableList<Plan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent plan as the given argument.
     */
    public boolean contains(Plan toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a plan to the list.
     * The plan must not already exist in the list.
     */
    public void add(Plan toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePlanException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent plan from the list.
     * The plan must exist in the list.
     */
    public void remove(Plan toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PlanNotFoundException();
        }
    }

    /**
     * Marks the equivalent plan as completed in the list.
     * The plan must exist in the list.
     */
    public void complete(Plan toComplete) {
        requireNonNull(toComplete);
        if (!contains(toComplete)) {
            throw new PlanNotFoundException();
        } else {
            for (Plan plan : internalList) {
                if (plan.equals(toComplete)) {
                    int index = internalList.indexOf(toComplete);
                    plan.setCompleted();
                    internalList.set(index, plan);
                }
            }
        }
    }

    /**
     * Unmarks the equivalent plan as uncomplete in the list.
     * The plan must exist in the list.
     */
    public void uncomplete(Plan toUncomplete) {
        requireNonNull(toUncomplete);
        if (!contains(toUncomplete)) {
            throw new PlanNotFoundException();
        } else {
            for (Plan plan : internalList) {
                if (plan.equals(toUncomplete)) {
                    int index = internalList.indexOf(toUncomplete);
                    plan.setUncompleted();
                    internalList.set(index, plan);
                }
            }
        }
    }

    /**
     * Replaces the plan {@code target} in the list with {@code editedPlan}.
     * {@code target} must exist in the list.
     * The plan identity of {@code editedPlan} must not be the same as another existing plan in the list.
     */
    public void setPlan(Plan target, Plan editedPlan) {
        requireAllNonNull(target, editedPlan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PlanNotFoundException();
        }

        if (!target.isSamePlan(editedPlan) && contains(editedPlan)) {
            throw new DuplicatePlanException();
        }

        internalList.set(index, editedPlan);
    }

    public void setPlans(List<Plan> plans) {
        requireAllNonNull(plans);
        if (!plansAreUnique(plans)) {
            throw new DuplicatePlanException();
        }

        internalList.setAll(plans);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePlanList)) {
            return false;
        }

        UniquePlanList otherUniquePlanList = (UniquePlanList) other;
        return internalList.equals(otherUniquePlanList.internalList);
    }

    public ObservableList<Plan> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    public Iterator<Plan> iterator() {
        return internalList.iterator();
    }

    private boolean plansAreUnique(List<Plan> plans) {
        for (int i = 0; i < plans.size() - 1; i++) {
            for (int j = i + 1; j < plans.size(); j++) {
                if (plans.get(i).equals(plans.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
