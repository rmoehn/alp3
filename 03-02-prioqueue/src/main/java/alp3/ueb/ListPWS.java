package alp3.ueb;

import java.lang.Comparable;
import java.util.LinkedList;
import java.util.List;

/**
 * A priority queue using a {@link LinkedList} as its internal data structure.
 * This might be called a decorator? Or not?
 */
public final class ListPWS<K extends Comparable<K>, V>
        implements PrioritätsWarteschlange<K, V> {
    private List<Eintrag<K, V>> list = new LinkedList<Eintrag<K, V>>();

    /**
     * Creates a new intance of this class.
     */
    public ListPWS() { }

    /**
     * Adds a new {@link Eintrag} to this priority queue.
     */
    public void einfuege(Eintrag<K, V> eintrag) {
        list.add(eintrag);
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
     * Returns the element with the highest priority (that is, the smallest
     * key).
     *
     * @throws LeereWarteschlangeException if this priority queue is empty
     */
    public Eintrag<K, V> min() throws LeereWarteschlangeException {
        if (list.isEmpty()) {
            throw new LeereWarteschlangeException();
        }

        return list.get(minIndex());
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

        return list.remove(minIndex());
    }

    /**
     * Returns the index of the element with the highest priority (that is,
     * the smallest key).
     */
    private int minIndex() {
        // Find the minimum's index using the old high water algorithm
        int curMinInd = 0;
        K curMinKey   = list.get(curMinInd).getSchluessel();
        for (int i = 1; i < list.size(); ++i) {
            Eintrag<K, V> curEntry = list.get(i);

            if (curMinKey.compareTo( curEntry.getSchluessel() ) > 0) {
                curMinInd = i;
                curMinKey = curEntry.getSchluessel();
            }
        }

        return curMinInd;
    }
}
