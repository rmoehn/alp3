package alp3.ueb;

import java.util.List;

public class RandomisedSlowsort extends Slowsort {
    /**
     * Sorts a list of comparable objects and returns the sorted list.
     */
    static <E extends Comparable<E>> List<E> sort(List<E> list) {
        for (Permutations<E> perms = new RandomPermutations<E>(list);
                perms.hasMoreElements(); ) {
            List<E> perm = perms.nextElement();

            if (isSorted(perm)) {
                return perm;
            }
        }

        throw new AssertionError("Should never be reached.");
    }
}
