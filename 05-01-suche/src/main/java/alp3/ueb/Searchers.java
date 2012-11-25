package alp3.ueb;

import java.lang.Math;

/**
 * Holder for various implementations of searches on arrays. This is a rather
 * silly class though.
 */
public final class Searchers {
    public static final int NOT_FOUND = -1;

    /**
     * Strategy for calculating an index for a dividing element in a list on
     * which some sort of search is to be performed.
     */
    private interface DividerStrategy {
        /**
         * Returns a dividing index between {@code lowerInd} (inclusive) and
         * {@code upperInd} (exclusive). {@code key} and {@code array} may not
         * be used by implementations.
         */
        public int calcDivider(int key, int[] array, int lowerInd,
                               int upperInd);
    }

    /**
     * Returns roughly the middle between two specified indices.
     */
    private static final DividerStrategy BINARY_DIVIDER
        = new DividerStrategy() {
              public int calcDivider(int key, int[] array, int lowerInd,
                                     int upperInd) {
                  return (lowerInd + upperInd - 1) / 2;
              }
          };

    /**
     * Returns an interpolated dividing element between the two indices.
     * Interpolated means that the relation between the elements at the lower
     * and upper index and the key is used to calculate a dividing index that
     * is (probably) quite near to the key.
     */
    private static final DividerStrategy INTERPOLATED_DIVIDER
        = new DividerStrategy() {
              public int calcDivider(int key, int[] array, int lowerInd,
                                     int upperInd) {
                  // Prevent division by zero
                  if (lowerInd == upperInd - 1) {
                      return lowerInd;
                  }

                  double l  = lowerInd;
                  double u  = upperInd - 1;
                  double k  = key;
                  double al = array[lowerInd];
                  double au = array[upperInd - 1];

                  int divider
                      = (int) (l + Math.abs(k - al) / (au - al) * (u - l));
                  return divider >= upperInd ? upperInd - 1 : divider;
              }
          };

    /**
     * Returns the index of the specified key in the array (must be sorted),
     * if present.
     */
    private static int generalSearch(int[] array, int key,
                                     DividerStrategy strategy) {
        /*
         * We use an iterative version of the known algorithm. First the upper
         * index and lower index are set to indicate the whole array. We then
         * look at some index (calculated with a strategy) and search the part
         * left of it or right of it, depending on the element at that index.
         * (Of course, we stop if that element is the sought one.)
         */
        int lowerInd   = 0;
        int upperInd   = array.length; // exclusive, as usual
        int dividerInd = 0;

        // As long as our search space contains more than one element
        while (upperInd > lowerInd) {
            // Find an element that divides it into two parts
            dividerInd = strategy.calcDivider(key, array, lowerInd, upperInd);

            // Look right of the dividing element if it is less than our key
            if (array[dividerInd] < key) {
                lowerInd = dividerInd + 1;
            }
            // Look left of the dividing element if it is greater than our key
            else if (array[dividerInd] > key) {
                upperInd = dividerInd;
            }
            // We must have found it!
            else {
                return dividerInd;
            }
        }

        return array[dividerInd] == key ? dividerInd : NOT_FOUND;
            // We could obtain more information from dividerInd, of course.
    }

    public static int binarySearch(int[] array, int key) {
        return generalSearch(array, key, BINARY_DIVIDER);
    }

    public static int interpolationSearch(int[] array, int key) {
        return generalSearch(array, key, INTERPOLATED_DIVIDER);
    }
}
