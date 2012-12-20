package alp3.ueb;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.MutablePair;

/**
 * Container class for methods encoding and decoding a text using Huffman's
 * method.
 */
public class Huffman {
    /**
     * Arguments:
     *
     * 0 - encode|decode
     * 1 - input file
     * 2 - output file
     * 3 - file for Huffman tree and other information
     * 4 - number of bytes to encode with one code (only for encoding)
     */
    public static void main (String[] args) {
        try {
            switch (args[0]) {
                case "encode":
                    encode(args[1], args[2], args[3],
                           Integer.parseInt(args[4]));
                    break;
                case "decode":
                    decode(args[1], args[2], args[3]);
                    break;
                default:
                    throw new IllegalArgumentException("Wrong argument.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("There was some error.");
        }
    }

    /**
     * Encodes the data in {@code infile} using Huffman's method and stores
     * the encoded data in {@code outfile} and the data representing the
     * Huffman tree in {@code treefile}. There are no characters but bytes
     * read from the input file. {@code bytesAtOnce} (at most four) bytes form
     * one leave of the Huffman tree.
     */
    public static void encode(String infile, String outfile, String treefile,
            int bytesAtOnce) throws FileNotFoundException, IOException {
        // Determine the maximum number of different leaves
        assert(bytesAtOnce > 0);
        assert(bytesAtOnce < 5);
        int maxLeavesCnt = 2^(bytesAtOnce * 8);

        // Open the input file for reading
        InputStream inStream
            = new BufferedInputStream( new FileInputStream(infile) );

        // Count how often each byte combination occurs
        Map<Integer, Integer> frequencyOf
            = new HashMap<Integer, Integer>(maxLeavesCnt);
        int curBytes;
        while ((curBytes = readN(bytesAtOnce, inStream)) != -1) {
            // Increase the number of occurences of an already seen byte combo
            if (frequencyOf.containsKey(curBytes)) {
                int freq = frequencyOf.get(curBytes) + 1;
                frequencyOf.put(curBytes, freq);
            }
            // Create new entry for yet unseen byte combination
            else {
                frequencyOf.put(curBytes, 1);
            }
        }

        // Create nodes for the Huffman tree from the frequencies
        Queue< MutablePair<Integer, List<Integer>> > huffmanNodes
            = new PriorityQueue< MutablePair<Integer, List<Integer>> >(
                  frequencyOf.size(),
                  new Comparator< Pair<Integer, ?> >() {
                      public int compare(Pair<Integer, ?> node0,
                                         Pair<Integer, ?> node1) {
                          return node0.getLeft() - node1.getLeft();
                      }
                  }
              );
        for (Map.Entry<Integer, Integer> entry : frequencyOf.entrySet()) {
            // Create list of byte combinations belonging to this node
            List<Integer> byteCombos = new LinkedList<Integer>();
            byteCombos.add(entry.getKey());

            // Add the weight and the list of combos to the priority queue
            huffmanNodes.add(
                new MutablePair<Integer, List<Integer>>(
                    entry.getValue(),
                    byteCombos
                )
            );
        }

        // As long as we haven't joined all small Huffman trees into one
        Map<Integer, SensibleBitSet> codeFor
            = new HashMap<Integer, SensibleBitSet>(maxLeavesCnt);
        while (huffmanNodes.size() > 1) {
            // Remove the two smallest nodes from the queue
            MutablePair<Integer, List<Integer>> node0 = huffmanNodes.remove();
            MutablePair<Integer, List<Integer>> node1 = huffmanNodes.remove();

            // Append 0 to the codes of all bytes combos in the first node
            extendCodes(node0.getRight(), codeFor, false);

            // Append 1 to the codes of all bytes combos in the first node
            extendCodes(node1.getRight(), codeFor, true);

            // Join the two nodes and reinsert the joint into the queue
            int newWeight = node0.getLeft() + node1.getLeft();
            node0.setLeft(newWeight);
            node0.getRight().addAll(node1.getRight());
            huffmanNodes.add(node0);
        }

        // Go through the table just created
        Map<SensibleBitSet, Integer> byteCombFor
            = new HashMap<SensibleBitSet, Integer>(maxLeavesCnt);
        int minCodeSize = Integer.MAX_VALUE;
        int maxCodeSize = 0;
        for (Map.Entry<Integer, SensibleBitSet> entry : codeFor.entrySet()) {
            // Reverse the code for the byte combination
            SensibleBitSet code    = entry.getValue();
            int codeSize   = code.size();
            SensibleBitSet revCode = new SensibleBitSet(codeSize);
            for (int i = 0; i < codeSize; ++i) {
                revCode.set(codeSize - i - 1, code.get(i));
            }

            // Put the reversed code in the map
            entry.setValue(revCode);

            // Add a mapping from the bit vector to the byte combination
            byteCombFor.put(entry.getValue(), entry.getKey());

            // Update the value for the shortest code if this one is shorter
            if (codeSize < minCodeSize) {
                minCodeSize = codeSize;
            }

            // Update the value for the longest code if this one is longer
            if (codeSize > maxCodeSize) {
                maxCodeSize = codeSize;
            }
        }

        // Go back to the start of the input file
        inStream = new BufferedInputStream(new FileInputStream(infile));
            // Is there a better way to do this?

        // Encode the file and store the code in a giant bit vector
        SensibleBitSet encoded = new SensibleBitSet();
        while ((curBytes = readN(bytesAtOnce, inStream)) != -1) {
            appendToSensibleBitSet(encoded, codeFor.get(curBytes));
        }
        inStream.close();

        // Write the SensibleBitSet as a byte array to the output file
        OutputStream outStream = new FileOutputStream(outfile);
        outStream.write(encoded.toByteArray());
        outStream.close();

        // Write the information necessary for decoding into a file
        ObjectOutputStream treeOutStream
            = new ObjectOutputStream(new FileOutputStream(treefile));
        treeOutStream.writeInt(bytesAtOnce);
        treeOutStream.writeInt(minCodeSize);
        treeOutStream.writeInt(maxCodeSize);
        treeOutStream.writeObject(byteCombFor);
        treeOutStream.close();
    }

    /** Decodes the data encoded using Huffman's method in {@code infileName}
     * with the information (Huffman tree equivalent) in {@code treefile} and
     * stores the result in {@code outfile}.
     */
    public static void decode(String infileName, String outfile,
            String treefile) throws FileNotFoundException, IOException,
            ClassNotFoundException {
        /*
         * Decoding using Huffman's method usually works like this: Bit after
         * bit is read from the input and a path in a tree is followed
         * according to this bits until a leaf is reached and this leaf
         * contains the byte combination encoded by the bits read.
         *
         * However, instead of a tree we use a Map: we read bit after bit,
         * building up a little bit vector. Every time a bit is read, the new
         * bit vector is looked up in the map. If it is found, we have got the
         * byte combination encoded by that bit vector. If not, we try with
         * the next bit.
         *
         * This looking up is bounded by two values obtained during encoding:
         * We start with looking up minCodeSize bits from the bit vector, as
         * it would be a waste of time looking up shorter codes that don't
         * exist anyway. We stop decoding if there is no map entry found after
         * trying a code of maxCodeSize bits.
         */

        // Read the information for decoding
        ObjectInputStream treeInStream
            = new ObjectInputStream(new FileInputStream(treefile));
        int bytesAtOnce = treeInStream.readInt();
        int minCodeSize = treeInStream.readInt();
        int maxCodeSize = treeInStream.readInt();

        @SuppressWarnings("unchecked")
        Map<SensibleBitSet, Integer> byteCombFor
            = (Map<SensibleBitSet, Integer>) treeInStream.readObject();
            // This is safe, because we wrote a HashMap

        treeInStream.close();

        // Read the whole encoded data into a bit vector
        File infile          = new File(infileName);
        InputStream inStream = new FileInputStream(infile);
        byte[] encodedBytes  = new byte[(int) infile.length()];
        inStream.read(encodedBytes);
        SensibleBitSet encoded       = SensibleBitSet.valueOf(encodedBytes);
        inStream.close();

        // Open the output file
        OutputStream outStream
            = new BufferedOutputStream(new FileOutputStream(outfile));

        // Go through the SensibleBitSet
        int bitSetOffset = 0;
        CODE:
        while (true) {
            // Try and read the code for one byte combination
            for (int codelen = minCodeSize; codelen <= maxCodeSize; ++codelen)
                    {
                // Look up codelen bits
                SensibleBitSet potentialCode
                    = encoded.get(bitSetOffset, bitSetOffset + codelen);
                Integer byteComb = byteCombFor.get(potentialCode);

                // If they represent a code
                if (byteComb != null) {
                    // Write the indicated byte combination to the output file
                    writeN(bytesAtOnce, byteComb, outStream);

                    // Go to the next code
                    bitSetOffset += potentialCode.size();
                    continue CODE;
                }
            }

            // There is nothing more to decode, so stop decoding
            break CODE;
        }

        // Close the output file
        outStream.close();
    }

    /**
     * Adds a 0 if {@code value} is {@code false} or a 1 if {@code value} is
     * {@code true} to the codes for all byte combinations in {@code
     * byteCombs}. Those codes are contained in {@code codeFor}.
     */
    private static void extendCodes(List<Integer> byteCombs,
            Map<Integer, SensibleBitSet> codeFor, boolean value) {
        // Go through the byte combinations
        for (Integer byteComb : byteCombs) {
            // Add a new empty code for a combination that hadn't been used
            if (! codeFor.containsKey(byteComb)) {
                codeFor.put(byteComb, new SensibleBitSet());
            }

            // Set the lowest bit to the desired value
            SensibleBitSet curCode = codeFor.get(byteComb);
            curCode.set(curCode.size(), value);
        }
    }

    /**
     * Reads {@code count} (at most four) bytes from {@code inStream} and
     * packs them into an int. Returns -1 if there is no more to read.
     */
    private static int readN(int count, InputStream inStream)
            throws IOException {
        assert(count > 0);
        assert(count < 5);

        int res       = 0;
        byte[] buffer = new byte[count];

        // Read in the number of bytes
        int readCnt = inStream.read(buffer);
        if (readCnt == -1) {
            return -1;
        }

        // Go through the bytes and store them in an int, one after the other
        for (int i = 0; i < readCnt; ++i) {
            res <<= i;
            res  += buffer[i];
        }

        return res;
    }

    /**
     * Writes {@code count} (at most four) bytes packed together in the {@code
     * int} {@code bytes} to {@code outStream}.
     */
    private static void writeN(int count, int bytes, OutputStream outStream)
            throws IOException {
        assert(count > 0);
        assert(count < 5);

        byte[] buffer = new byte[count];

        // Transform the int into an array of bytes
        for (int i = 0; i < count; ++i) {
            buffer[count - i - 1] = (byte) (bytes & (0xff << i));
        }

        outStream.write(buffer);
    }

    /**
     * Appends {@link SensibleBitSet} {@code part} to {@code SensibleBitSet} {@code whole}.
     */
    private static void appendToSensibleBitSet(SensibleBitSet whole, SensibleBitSet part) {
        int wholeSize = whole.size();
        int partSize  = part.size();

        for (int i = 0; i < partSize; ++i) {
            whole.set(wholeSize + i, part.get(i));
        }
    }
}
