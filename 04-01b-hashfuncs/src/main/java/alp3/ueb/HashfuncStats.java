package alp3.ueb;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class for testing the quality of hash functions.
 */
public class HashfuncStats {
    // The inverse of the golden section, according to Ottmann, Widmayer:
    // Algorithmen und Datenstrukturen, 4th ed., 2002
    private static double invGoldSec = 0.6180339887;

    private static List<Hasher<String>> hashers
        = new ArrayList<Hasher<String>>();

    /**
     * A strategy for hashing strings.
     */
    private interface Hasher<T> {
        /**
         * Returns a hash value of an object of type {@code T}. The results
         * may range from -2^31 to 2^31-1.
         */
        public int run(T t);
    }

    /**
     * Runs all registered hash functions over all words in a text and
     * measures the frequency of the different results.
     *
     * @param 0 the maximum value of the output hashes
     * @param 1 the file with the words to hash
     */
    public static void main (String[] argv) throws FileNotFoundException {
        final int tableSize = Integer.parseInt(argv[0]);

        // The following hash functions return values between 0 and tableSize.

        // Hashes a string with Java's built-in hashCode()
        hashers.add(new Hasher<String>() {
            public int run(String string) {
                return mymod(string.hashCode(), tableSize);
            }
        });

        // Hashes a string by calculating the sum of its characters
        hashers.add(new Hasher<String>() {
            public int run(String string) {
                return mymod(charSum(string), tableSize);
            }
        });

        // Hashes a string with the muliplicative method on its character sum
        hashers.add(new Hasher<String>() {
            public int run(String string) {
                return multMeth(charSum(string), tableSize);
            }
        });

        // Hashes a string by calculating the product of its characters
        hashers.add(new Hasher<String>() {
            public int run(String string) {
                return mymod(charProd(string), tableSize);
            }
        });

        // Hashes a string with the muliplicative method on its char product
        hashers.add(new Hasher<String>() {
            public int run(String string) {
                return multMeth(charProd(string), tableSize);
            }
        });

        // Hashes a string as proposed in Effective Java by Joshua Bloch
        hashers.add(new Hasher<String>() {
            public int run(String string) {
                char[] chars = string.toCharArray();
                int result = 17;

                for (char c : chars) {
                    result = 31 * result + c;
                }

                return mymod(result, tableSize);
            }
        });


        // The array to hold the hash value frequencies by hasher
        int[][] frequencies = new int[tableSize][hashers.size()];

        // Create a scanner for splitting the input file into words
        Scanner inputWords = new Scanner(new File(argv[1]));
        inputWords.useDelimiter("\\s+");

        // For all words from our input text...
        while (inputWords.hasNext()) {
            String word = inputWords.next();

            // ...apply all hashers to it
            for (int i = 0; i < hashers.size(); ++i) {
                int hashval = hashers.get(i).run(word);

                // Increment the frequency of this particular hash value
                ++frequencies[hashval][i];
            }
        }

        // Print the frequencies to standard output for each hash value...
        for (int hashval = 0; hashval < frequencies.length; ++hashval) {
            int[] freqsForHashval = frequencies[hashval];
            String hashValLine = Integer.toString(hashval);

            // ...with columns for the different hashers
            for (int freqForHasher : freqsForHashval) {
                hashValLine += "\t";
                hashValLine += Integer.toString(freqForHasher);
            }

            System.out.println(hashValLine);
        }
    }

    /**
     * Returns the sum of all char values in a string.
     */
    private static int charSum(String string) {
        char[] chars = string.toCharArray();
        int result = 0;

        for (char c : chars) {
            result += c;
        }

        return result;
    }

    /**
     * Returns the product of all char values in a string.
     */
    private static int charProd(String string) {
        char[] chars = string.toCharArray();
        int result = 1;

        for (char c : chars) {
            result *= c;
        }

        return result;
    }

    /**
     * Returns the hash of k for the specified hash table size, calculated
     * with the multiplicative method.
     *
     * See also: the Ottmann/Widmayer book mentioned above
     */
    private static int multMeth(int k, int tableSize) {
        return (new Double(
                   Math.floor(
                       tableSize * (
                           k * invGoldSec - Math.floor(k * invGoldSec)
                       )
                   )
               )).intValue();
    }

    /**
     * Returns the remainder of the division of n1 by n2. This implementation,
     * as opposed to Java's, returns only positive numbers.
     */
    private static int mymod(int n1, int n2) {
        int rem = n1 % n2;

        return rem >= 0 ? rem : rem + n2;
    }
}
