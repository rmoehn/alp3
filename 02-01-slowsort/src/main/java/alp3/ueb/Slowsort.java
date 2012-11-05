package alp3.ueb;

import java.util.List;
import java.util.ArrayList;
import java.lang.Comparable;

public class Slowsort {
    /**
     * Sorts a list of comparable objects and returns the sorted list.
     */
    static <E extends Comparable<E>> List<E> sort(List<E> list) {
        for (Permutations perms = new Permutations(list);
                perms.hasMoreElements(); ) {
            List<E> perm = perms.nextElement();

            if (isSorted(perm)) {
                return perm;
            }
        }

        throw new AssertionError("Should never be reached.");
    }

    /**
     * Checks whether a list is sorted ascendingly.
     */
    static <E extends Comparable<E>> boolean isSorted(List<E> list) {
        // Check whether every element is less or equal than that following
        for (int i = 0; i < list.size() - 1; ++i) {
            if ( list.get(i).compareTo( list.get(i+1) ) == 1 ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Generates a list of all permutations of the given list.
     */
    static <E> List<List<E>> permutations(List<E> list) {
        int size = list.size();

        // Anchor: one-element lists have only one permutations
        if (size == 1) {
            List<List<E>> newList = new ArrayList<List<E>>();
            newList.add(list);
            return newList;
        }

        // Pick one element after the other
        List<List<E>> perms = new ArrayList<List<E>>();
        for (int i = 0; i < size; ++i) {
            // Remove it from this list
            E curElement = list.remove(i);

            // Append it to all permutations of the rest of the list
            perms = permutations(list);
            for (List<E> subperm : perms) {
                subperm.add(curElement);
            }
        }

        return perms;
    }
}
