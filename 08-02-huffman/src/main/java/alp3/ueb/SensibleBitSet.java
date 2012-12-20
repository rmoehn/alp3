package alp3.ueb;

import java.util.BitSet;
import java.io.Serializable;

// THIS CLASS SHOULD NOT BE USED!

/**
 * {@link BitSets} don't know which is the highest bit that was touched, this
 * {@code SensibleBitSet} does. And there are some other bells and whistles.
 */
public class SensibleBitSet implements Serializable {
    private static final long serialVersionUID = 4429696L;
    private BitSet bitSet;
    private int highestTouchedBit = -1;

    public SensibleBitSet() {
        bitSet = new BitSet();
    }

    public SensibleBitSet(int nbits) {
        bitSet = new BitSet(nbits);
    }

    private SensibleBitSet(BitSet bitSet) {
        this.bitSet = bitSet;
    }

    public void set(int bitIndex, boolean value) {
        if (bitIndex > highestTouchedBit) {
            highestTouchedBit = bitIndex;
        }

        bitSet.set(bitIndex, value);
    }

    /**
     * Doesn't return the internal size of this {@code BitSet}, but returns
     * the offset of the highest bit that has been touched plus one.
     */
    public int size() {
        return highestTouchedBit + 1;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(size());

        for (int i = 0; i <= highestTouchedBit; ++i) {
            builder.append(bitSet.get(i) ? '1' : '0');
        }

        builder.reverse();

        return builder.toString();
    }

    public boolean get(int index) {
        if (index > highestTouchedBit) {
            throw new IndexOutOfBoundsException("This bit is not set.");
        }

        return bitSet.get(index);
    }

    public SensibleBitSet get(int fromIndex, int toIndex) {
        if (toIndex - 1 > highestTouchedBit) {
            throw new IndexOutOfBoundsException("This bit is not set.");
        }

        SensibleBitSet newBS
            = new SensibleBitSet(bitSet.get(fromIndex, toIndex));
        newBS.setSize(toIndex - fromIndex);

        return newBS;
    }

    public static SensibleBitSet valueOf(byte[] bytes) {
        SensibleBitSet newBS = new SensibleBitSet(BitSet.valueOf(bytes));
        newBS.setSize(bytes.length * 8);

        return newBS;
    }

    public byte[] toByteArray() {
        int bytesForThis = (highestTouchedBit + 1) / 8;
        if (bytesForThis % 8 != 0) {
            ++bytesForThis;
        }
        byte[] res = new byte[bytesForThis];

        System.arraycopy(bitSet.toByteArray(), 0, res, 0, bytesForThis);

        return res;
    }

    private void setSize(int newSize) {
        highestTouchedBit = newSize - 1;
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof SensibleBitSet)) {
            return false;
        }
        SensibleBitSet that = (SensibleBitSet) o;

        if (this.highestTouchedBit != that.highestTouchedBit) {
            return false;
        }

        return this.bitSet.equals(that.bitSet);
    }

    @Override public int hashCode() {
        int result = 17;
        result = 31 * result + bitSet.hashCode();
        result = 31 * result + highestTouchedBit;
        return result;
    }
}
