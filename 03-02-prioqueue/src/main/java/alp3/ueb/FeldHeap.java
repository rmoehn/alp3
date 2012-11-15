package alp3.ueb;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

/**
 * A priority queue using a heap as its internal data structure.
 */
public final class FeldHeap<K extends Comparable<K>, V>
        implements PrioritätsWarteschlange<K, V> {
    /*
     * Additional heap class would be better...
     */

    /*
     * The exercise called for an ordinary array. However, we can't create an
     * Eintrag<K, V>[], so this is out of question.
     */
    private List<Eintrag<K, V>> list = new ArrayList<Eintrag<K, V>>();

    /**
     * Creates a new instance of this class.
     */
    public FeldHeap() { }

    /**
     * Adds a new {@link Eintrag} to this priority queue.
     */
    public void einfuege(Eintrag<K, V> eintrag) {
        // Add the entry at the lower right end of the heap
        list.add(eintrag);
        K   eintragKey = eintrag.getSchluessel();
        int eintragInd = list.size() - 1;

        // As long as there are parents
        int parInd;
        while ((parInd = parentInd(eintragInd)) >= 0) {
            Eintrag<K, V> parent = list.get(parInd);

            // If the new element has a higher priority (lower value)
            if (parent.getSchluessel().compareTo(eintragKey) > 0) {
                // Swap it with its parent
                list.set(parInd,     eintrag);
                list.set(eintragInd, parent);

                // Look at the element's new position in the next iteration
                eintragInd = parInd;
            }
            // Stop swapping on first encounter of a higher-priority parent
            else {
                break;
            }
        }

        return;
    }

    /**
     * Checks whether this priority queue is empty.
     */
    public boolean istLeer() {
        return list.isEmpty();
    }

    /**
     * Returns the number of elements in this priority queue.
     */
    public int groesse() {
        return list.size();
    }

    /**
     * Returns and removes the element with the highest priority (that is,
     * the smallest key).
     *
     * @throws LeereWarteschlangeException if this priority queue is empty
     */
    public Eintrag<K, V> streicheMin() throws LeereWarteschlangeException {
        if (list.isEmpty()) {
            throw new LeereWarteschlangeException();
        }

        // Store the highest-priority/lowest-value element for returning
        Eintrag<K, V> minEntry = list.get(0);

        // Get information about the lower rigth entry
        int replEntryInd        = list.size() - 1;
        Eintrag<K, V> replEntry = list.get(replEntryInd);
        K replEntryKey          = replEntry.getSchluessel();

        // Replace the top of the heap with the lower right entry, remove this
        list.set(0, replEntry);
        list.remove(replEntryInd);

        // As long as there are children
        int rightChildInd;
        while ((rightChildInd = rightInd(replEntryInd)) < list.size()) {
            int leftChildInd = leftInd(replEntryInd);
            Eintrag<K, V> leftChild  = list.get(leftChildInd);
            Eintrag<K, V> rightChild = list.get(rightChildInd);

            int minChildInd;
            Eintrag<K, V> minChild;

            // Determine the high-priority/lower-value of the children
            if (leftChild.getSchluessel()
                         .compareTo( rightChild.getSchluessel() ) <= 0) {
                minChildInd = leftChildInd;
                minChild    = leftChild;
            }
            else {
                minChildInd = rightChildInd;
                minChild    = rightChild;
            }

            // If the replacing element has lower priority than its children
            if (minChild.getSchluessel().compareTo(replEntryKey) < 0) {
                // Swap them
                list.set(minChildInd, replEntry);
                list.set(replEntryInd, minChild);

                // Look at the element's new position in the next iteration
                replEntryInd = minChildInd;
            }
            // Stop swapping on first encounter of a lower-priority child
            else {
                break;
            }
        }

        return minEntry;
    }

    /**
     * Returns the element with the highest priority (that is, the smallest
     * key).
     *
     * @throws LeereWarteschlangeException if this priority queue is empty
     */
    public Eintrag<K, V> min() throws LeereWarteschlangeException {
        if (list.isEmpty()) {
            throw new LeereWarteschlangeException();
        }

        return list.get(0);
    }

    /**
     * Returns the index of the left child of the specified index.
     */
    private int leftInd(int parentInd) {
        return 2 * parentInd + 1;
    }

    /**
     * Returns the index of the right child of the specified index.
     */
    private int rightInd(int parentInd) {
        return 2 * parentInd + 2;
    }

    /**
     * Returns the index of the parent of the specified index.
     */
    private int parentInd(int childInd) {
        return (new Double(Math.ceil(childInd * 0.5))).intValue() - 1;
    }
}
