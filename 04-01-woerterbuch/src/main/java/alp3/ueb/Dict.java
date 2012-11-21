package alp3.ueb;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * A simple dictionary.
 */
public final class Dict<K extends Comparable<K>, V>
        implements WoerterBuch<K, V> {
    // Comparable<K> needed only for compliance with Eintrag

    /*
     * THIS CLASS NEEDS REFACTORING!
     */

    private static final int INITIAL_SIZE  = 20;
    private static final float LOAD_FACTOR = 0.7f;

    private List<List<Eintrag<K, V>>> table
        = new ArrayList<List<Eintrag<K, V>>>(INITIAL_SIZE);
    private int curListLength = INITIAL_SIZE;
    private int entryCnt = 0;

    /**
     * Creates a new instance of this class.
     */
    public Dict() { }

    /**
     * Inserts an element into this dictionary.
     */
    public void einfuege(Eintrag<K, V> entry) {
        // Determine the index of the new element in the hashtable
        int index = calcBucketFor(entry);

        // If we haven't been so far out yet, initialise those elements
        if (index == -1) {
            index = calcNewBucketFor(entry);
            initBuckets(index);
        }

        // Add the entry to its hashbucket
        table.get(index).add(entry);

        // Increment the number of elements in this dictionary
        ++entryCnt;

        // Rehash this dictionary if we have too many entries in it
        if (entryCnt > LOAD_FACTOR * curListLength) {
            rehash();
        }
    }

    /**
     * Returns the first entry with the specified key in this dictionary or
     * {@code null} if it doesn't exist.
     */
    public Eintrag<K, V> finde(K key) {
        // Find the hash bucket
        int bucketInd = calcBucketFor(key);
        if (bucketInd == -1) {
            return null;
        }
        List<Eintrag<K, V>> bucket = table.get(bucketInd);

        // Look our key up
        for (Eintrag<K, V> entry : bucket) {
            if (entry.getSchluessel().equals( key )) {
                return entry;
            }
        }

        return null;
    }

    /**
     * Returns all entries with the specified key in this dictionary or an
     * empty collection if they don't exist.
     */
    public Collection<Eintrag<K, V>> findeAlle(K key) {
        Collection<Eintrag<K, V>> resColl = new LinkedList<Eintrag<K, V>>();

        // Find the hash bucket
        int bucketInd = calcBucketFor(key);
        if (bucketInd == -1) {
            return resColl;
        }
        List<Eintrag<K, V>> bucket = table.get(bucketInd);

        // Find all elements with the specified key
        for (Eintrag<K, V> entry : bucket) {
            if (entry.getSchluessel().equals( key )) {
                resColl.add(entry);
            }
        }

        return resColl;
    }

    /**
     * Removes the first element that equals the specified entry from this
     * dictionary.
     *
     * @throws NoSuchElementException if there is no such entry in this
     * dictionary
     */
    public void streiche(Eintrag<K, V> entry) {
        // Find the hash bucket
        int bucketInd = calcBucketFor(entry);
        if (bucketInd == -1) {
            throw new NoSuchElementException(
                "Can't remove nonexistent entry.");
        }
        List<Eintrag<K, V>> bucket = table.get(bucketInd);

        // Attempt to find such an element
        int index;
        if ((index = bucket.indexOf(entry)) == -1) {
            // Raise an exception if we can't
            throw new NoSuchElementException(
                "Can't remove nonexistent entry.");
        }

        // Delete it
        bucket.remove(index);

        // Decrement the number of elements in this dictionary
        --entryCnt;
    }

    /**
     * Returns the number of elements in this dictionary.
     */
    public int groesse() {
        return entryCnt;
    }

    /**
     * Returns {@code true} if this dictionary contains no elements.
     */
    public boolean istLeer() {
        return entryCnt == 0;
    }

    /**
     * Returns the index of the bucket for the specified entry or -1 if the
     * bucket doesn't exist.
     */
    private int calcBucketFor(Eintrag<K, V> entry) {
        return calcBucketFor(entry.getSchluessel());
    }

    /**
     * Returns the index of the bucket for the specified key or -1 if the
     * bucket doesn't exist.
     */
    private int calcBucketFor(K key) {
        int index = calcNewBucketFor(key);

        if (index >= table.size()) {
            return -1;
        }
        else {
            return index;
        }
    }

    /**
     * Returns the index of the bucket for the specified key even if it
     * doesn't exist.
     */
    private int calcNewBucketFor(K key) {
        return mymod(key.hashCode(), curListLength);
    }

    /**
     * Returns the index of the bucket for the specified entry even if it
     * doesn't exist.
     */
    private int calcNewBucketFor(Eintrag<K, V> entry) {
        return calcNewBucketFor(entry.getSchluessel());
    }

    /**
     * Initialises the hashbuckets of the internal list up to the specified
     * index.
     */
    private void initBuckets(int index) {
        for (int i = table.size(); i <= index; ++i) {
            table.add(new LinkedList<Eintrag<K, V>>());
        }
    }

    /**
     * Doubles the size of the internal array and reinserts all elements.
     */
    private void rehash() {
        // Save the old internal array
        List<List<Eintrag<K, V>>> oldTable = table;

        // Replace it with a larger one
        table         = new ArrayList<List<Eintrag<K, V>>>(2 * curListLength);
        curListLength = 2 * curListLength;
        entryCnt      = 0;

        // Reinsert the elements from the old table
        for (List<Eintrag<K, V>> bucket : oldTable) {
            for (Eintrag<K, V> entry : bucket) {
                einfuege(entry);
            }
        }
    }

    /**
     * Returns an always-positive remainder of the division of n1 by n2. This
     * is different from the Java %, but required for a hash.
     */
    private static int mymod(int n1, int n2) {
        int res = n1 % n2;

        return res < 0 ? res + n2 : res;
    }
}
