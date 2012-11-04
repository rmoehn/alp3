package alp3.ueb;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * An enumeration/iterator that returns the permutations of a collection of
 * objects, one after the other.
 */
class Permutations<E> implements Enumeration {
    /*
     * For a description of the algorithm used, see also: M. J. Dominus:
     * Higher Order Perl. Transforming Programs with Programs, 2005, Elsevier.
     * pp. 128-135 (also available online)
     */

    private int curPermNr = 0;
    private ArrayList<E> items;
    private int itemsCnt;

    /**
     * Creates a new instance of this class.
     */
    public Permutations(Collection<? extends E> items) {
        this.items    = new ArrayList(items);
        this.itemsCnt = items.size();
    }

    /**
     * Returns the next permutation.
     *
     * @throws NoSuchElementException if all permutations are exhausted
     */
    public List<E> nextElement() {
        int[] pattern;

        // Obtain the pattern for this permutation
        pattern = numberToPattern(curPermNr);
        if (pattern.length == 0) {
            throw new NoSuchElementException("Permutations exhausted.");
        }

        // Increment the number of permutations
        ++curPermNr;

        // Return the current permutation
        return patternToPermutation(pattern);
    }

    /**
     * Checks whether there are more permutations available.
     */
    public boolean hasMoreElements() {
        if (numberToPattern(curPermNr).length == 0) {
            return false;
        }

        return true;
    }

    /**
     * Computes a permutation of this iterator's elements from a given
     * pattern.
     */
    private List<E> patternToPermutation(int[] pattern) {
        List<E> perm           = new ArrayList<E>(itemsCnt);
        ArrayList<E> tempItems = new ArrayList<E>(items);

        // Remove elements determined by the pattern and append them to result
        for (int indexToSelect : pattern) {
            perm.add(tempItems.remove(indexToSelect));
        }

        return perm;
    }

    /**
     * Generates a pattern from which the n-th permutation of the items is
     * computed.
     */
    private int[] numberToPattern(int n) {
        /*
         * The idea behind this is an odometer with wheels of different sizes.
         * The first wheel has size itemsCnt, the second itemsCnt-1 and so on.
         * This algorithm calculates the position of the wheels after n
         * incrementations.
         *
         * Another way of looking at it is converting a base-10 number to a
         * variable-base number using Euclid's algorithm (Was it Euclid?).
         */

        int[] pattern = new int[itemsCnt];

        for (int i = 1; i <= itemsCnt; ++i) {
            pattern[itemsCnt - i] = n % i;
            n /= i;
        }

        return n == 0 ? new int[0] : pattern;
    }

    /**
     * Returns the factorial of the given number {@code >= 0}.
     */
    static int factorial(int n) {
        // Prevent bad results
        if (n < 0) {
            throw new
                IllegalArgumentException("Number must not be less than 0.");
        }

        // Calculate the product
        int result = 1;
        for (int i = 2; i <= n; ++i) {
            result *= i;
        }

        return result;
    }
}
