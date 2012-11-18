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

    /**
     * Compares this {@code Entry} with an object for equality.
     */
    @Override public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (getClass() != o.getClass()) {
            return false;
        }

        // We have just checked that the classes are equal, so this is safe
        @SuppressWarnings("unchecked")
        Eintrag<K, V> that = (Eintrag<K, V>) o;

        return that.getSchluessel().equals(key)
            && (value == null ? that.getWert() == null
                              : that.getWert().equals(value)
                              );
    }

    /**
     * Returns the hash code of this object.
     */
    @Override public int hashCode() {
        int result = 17;
        result = 31 * result + key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
