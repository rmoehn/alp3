package alp3.ueb;

import java.util.List;
import java.util.ArrayList;
import java.lang.Comparable;

public class Slowsort {
    /**
     * Sorts a list of comparable objects and returns the sorted list.
     */
    static <E extends Comparable<E>> List<E> sort(List<E> list) {
        for (Permutations<E> perms = new Permutations<E>(list);
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
}
