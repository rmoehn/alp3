package alp3.ueb;

import java.util.BitSet;
import java.io.Serializable;

// THIS CLASS SHOULD NOT BE USED!

/**
 * {@link BitSets} don't know which is the highest bit that was touched, this
 * {@code SensibleBitSet} does. And there are some other bells and whistles.
 */
public class SensibleBitSet extends BitSet implements Serializable {
    /*
     * Maybe this should not extend BitSet but rather delegate to it.
     */

    private static final long serialVersionUID = 4429696L;
    private int highestTouchedBit = -1;

    public SensibleBitSet() {
        super();
    }

    public SensibleBitSet(int nbits) {
        super(nbits);
    }

    @Override public void set(int bitIndex, boolean value) {
        if (bitIndex > highestTouchedBit) {
            highestTouchedBit = bitIndex;
        }

        super.set(bitIndex, value);
    }

    /**
     * Doesn't return the internal size of this {@code BitSet}, but returns
     * the offset of the highest bit that has been touched plus one.
     */
    @Override public int size() {
        return highestTouchedBit + 1;
    }

    @Override public String toString() {
        StringBuilder builder = new StringBuilder(size());

        for (int i = 0; i <= highestTouchedBit; ++i) {
            builder.append(super.get(i) ? '1' : '0');
        }

        builder.reverse();

        return builder.toString();
    }

    @Override public SensibleBitSet get(int fromIndex, int toIndex) {
        SensibleBitSet newBS = (SensibleBitSet) super.get(fromIndex, toIndex);
        newBS.setSize(toIndex - fromIndex);

        return newBS;
    }

    public static SensibleBitSet valueOf(byte[] bytes) {
        SensibleBitSet newBS = (SensibleBitSet) BitSet.valueOf(bytes);
        newBS.setSize(bytes.length * 8);

        return newBS;
    }

    private void setSize(int newSize) {
        highestTouchedBit = newSize - 1;
    }
}
