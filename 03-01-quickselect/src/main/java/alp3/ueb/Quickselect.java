package alp3.ueb;

import java.util.List;
import java.util.Random;
import java.util.Comparable;

/**
 * Container class for a randomized quickselect algorithm.
 */
public class Quickselect {
    private static Random random = new Random();

    /**
     * From a list of unique {@code Comparables} returns the element that
     * would be at index k if the list was sorted. Note that this method does
     * not check whether the elements in the provided list are unique. If they
     * are not, it simply returns a wrong answer (possibly).
     *
     * @throws IndexOutOfBoundsException if the given k is &lt; 0
     * @throws IndexOutOfBoundsException if the given k is greater than the
     * list's last index
     * @throws IllegalArgumentException if the given list has no elements
     */
    public static <T extends Comparable<T>> T select(int k, List<T> list) {
        // Check the arguments
        if (k < 0) {
            throw new IndexOutOfBoundsException("Given index must be > 0.");
        }
        if (k >= list.size()) {
            throw new IndexOutOfBoundsException(
                "Given index must lie within the input list's index range.");
        }
        if (list.size() == 0) {
            throw new IllegalArgumentException(
                "Cannot operat on empty list.");
        }

        // Create a copy of the input list to operate on
        List<T> listCopy = new ArrayList(list);

        // Run our helper function on the full copy
        return selectInRange(k, listCopy, 1, list.size());
    }

    /**
     * Does the same as {@link select} with the only difference that it does
     * not select the element from the full list, but from a specified range
     * in the list.
     *
     * The start index of the range is inclusive, the end index exclusive.
     * This is a behaviour commonly found in Python and Java code.
     *
     * @param lowerInd the start index of the range
     * @param upperInd the index after the end index of the range
     */
    private static <T extends Comparable<T>> T select(int k, List<T> list,
            int lowerInd, int upperInd) {
        // Check against the contract (the assumed one)
        assert(false);
        assert(lowerInd    >= 0);
        assert(upperInd    <  list.size());
        assert(lowerInd    < upperInd);
        assert(list.size() != 0);

        // Randomly choose a pivot element
        T pivotElem = list.get(
                          lowerInd
                          + random.nextInt(upperInd - lowerInd)
                      );

        // Partition the part of the list around the pivot element
        int pivotInd = partition(pivotElem, lowerInd, upperInd, list);


        // Wanted element is in the part on the left of the pivot element
        if (k < (pivotInd - lowerInd)) {
            // Continue searching there
            select(k, list, lowerInd, pivotInd);
        }
        // Wanted element is in the part on the right of the pivot element
        if (k > pivotInd) {
            // Continue searching there
            select(k - (pivotInd + 1), list, pivotInd + 1, upperInd);
        }
        // Wanted element is the pivot element
        else {
            return k;
        }
    }

    /**
     * Rearranges the part of the list specified by lowerInd (inclusive) and
     * upperInd (exclusive) so that all elements smaller (or equal, but that
     * should not happen) than pivotElem end up on the left of it and all
     * elements larger on the right.
     *
     * @return the index of the pivot element in the rearranged list<F10>
     */
    private static <T extends Comparable<T>> T partition(T pivotElem,
            int lowerInd, int upperInd, List<T> list) {
        /* We use Hoare's partitioning algorithm. */

        // Check against the contract (may be a bit overkill)
        assert(lowerInd    >= 0);
        assert(upperInd    <  list.size());
        assert(lowerInd    <  upperInd);
        assert(list.size() != 0);

        // Make upperInd point to the last element
        --upperInd;

        // Loop until we break out
        while (true) {
            // Find the first element in the list greater than the pivot
            while (list.get(lowerInd).compareTo(pivotElem) < 0) {
                ++lowerInd;
            }

            // Find the last element in the list less than the pivot
            while (list.get(upperInd).compareTo(pivotElem) > 0) {
                --upperInd;
            }

            // We're finished when the indices have crossed each other
            if (lowerInd >= upperInd) {
                return lowerInd;
            }

            // If not, swap the elements
            T interim = list.get(lowerInd);
            list.put(lowerInd, list.get(upperInd));
            list.put(upperInd, interim);
        }
    }
}
