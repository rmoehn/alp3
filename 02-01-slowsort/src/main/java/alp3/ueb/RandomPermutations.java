package alp3.ueb;

import java.util.Random;
import java.util.Collection;

/**
 * An enumeration/iterator that returns random permutations of a collection of
 * objects.
 */
public class RandomPermutations<E> extends Permutations<E> {
    Random random;

    public RandomPermutations(Collection<? extends E> items) {
        super(items);

        random = new Random();
    }

    /**
     * Creates a random pattern to construct a permutation from. The parameter
     * n is thrown away.
     */
    @Override int[] numberToPattern(int n) {
        int[] pattern = new int[itemsCnt];

        for (int i = 0; i < itemsCnt; ++i) {
            pattern[i] = random.nextInt(itemsCnt - i);
        }

        return pattern;
    }
}
