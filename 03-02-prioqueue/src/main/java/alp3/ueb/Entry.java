package alp3.ueb;

import java.lang.Comparable;

/**
 * Key-value pairs of some sort. The keys have to be comparable.
 */
final class Entry<K extends Comparable<K>, V> implements Eintrag<K, V> {
    private final K key;
    private final V value;

    /**
     * Creates a new instance of this class.
     */
    public Entry(K key, V value) {
        this.key   = key;
        this.value = value;
    }

    /**
     * Returns the key of this entry.
     */
    public K getSchluessel() {
        return key;
    }

    /**
     * Returns the value of this entry.
     */
    public V getWert() {
        return value;
    }
}
