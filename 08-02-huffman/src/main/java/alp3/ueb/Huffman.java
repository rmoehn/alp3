package alp3.ueb;

import java.io.File;

/**
 * Container class for methods encoding and decoding a text using Huffman's
 * method.
 */
public class Huffman {
    /**
     * Encodes the data in {@code infile} using Huffman's method and stores
     * the encoded data in {@code outfile} and the data representing the
     * Huffman tree in {@code treefile}. There are no characters but bytes
     * read from the input file. {@code bytesAtOnce} (at most four) bytes form
     * one leave of the Huffman tree.
     */
    public static void encode(String infile, String outfile, String treefile,
                              int bytesAtOnce) {
        // Determine the maximum number of different leaves
        assert(bytesAtOnce > 0);
        assert(bytesAtOnce < 5);
        int maxLeavesCnt = 2^(bytesAtOnce * 8);

        // Open the input file for reading
        InputStream inStream
            = new BufferedInputStream( new FileInputStream(infile) );

        // Count how often each byte combination occurs
        Map<ArrayList<Byte>, Integer> frequencyOf
            = new HashMap<Integer, Integer>(maxLeavesCnt);
        int curBytes;
        while ((curBytes = readN(bytesAtOnce, inStream)) != -1) {
            // Increase the number of occurences of an already seen byte combo
            if (frequencyOf.containsKey(curBytes)) {
                ++frequencyOf.get(curBytes);
            }
            // Create new entry for yet unseen byte combination
            else {
                frequencyOf.put(curBytes, 1);
            }
        }

        // Create nodes for the Huffman tree from the frequencies
        Queue< Pair<Integer, List<Integer>> > huffmanNodes
            = new PriorityQueue< Pair<Integer, List<Integer>> >(
                  frequencyOf.size()
              );
        for (Map.Entry<Integer, Integer> entry : frequencyOf) {
            // Create list of byte combinations belonging to this node
            List<Integer> byteCombos = new LinkedList<Integer>();
            byteCombos.add(entry.getValue());

            // Add the weight and the list of combos to the priority queue
            huffmanNodes.add(
                new MutablePair(
                    entry.getKey(),
                    byteCombos
                )
            );
        }

        // As long as we haven't joined all small Huffman trees into one
        Map<Integer, BitSet> codeFor
            = new HashMap<Integer, BitSet>(maxLeavesCnt);
        while (huffmanNodes.size() > 1) {
            // Remove the two smallest nodes from the queue
            Pair<Integer, List<Integer>> node0 = huffmanNodes.remove();
            Pair<Integer, List<Integer>> node1 = huffmanNodes.remove();

            // Append 0 to the codes of all bytes combos in the first node
            extendCodes(node0.getRight(), codeFor, false);

            // Append 1 to the codes of all bytes combos in the first node
            extendCodes(node1.getRight(), codeFor, true);

            // Join the two nodes and reinsert the joint into the queue
            node0.getLeft() += node1.getLeft();
            node0.getRight().addAll(node1.getRight());
            huffmanNodes.add(node0);
        }

        // Go through the table just created
        Map<BitSet, Integer> byteCombFor
            = new HashMap<BitSet, Integer>(maxLeavesCnt);
        int minCodeSize = Integer.MAX_VALUE;
        int maxCodeSize = 0;
        for (Map.Entry<Integer, BitSet> : codeFor) {
            // Reverse the code for the byte combination
            BitSet code    = codeFor.getValue();
            int codeSize   = code.size();
            BitSet revCode = new BitSet(codeSize);
            for (int i = 0; i < codeSize; ++i) {
                revCode.set(codeSize - i - 1, code.get(i));
            }

            // Put the reversed code in the map
            codeFor.setValue(revCode);

            // Add a mapping from the bit vector to the byte combination
            byteCombFor.put(codeFor.getValue(), codeFor.getKey());

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
        inStream.mark(0);
        inStream.reset();

        // Encode the file and store the code in a giant bit vector
        BitSet encoded = new BitSet();
        while ((curBytes = readN(bytesAtOnce, inStream)) != -1) {
            appendToBitSet(encoded, codeFor.get(curBytes));
        }
        inStream.close();


        // Write the BitSet as a byte array to the output file
        OutputStream outStream = new FileOutputStream(outfile);
        outStream.write(encoded.toByteArray());
        outStream.close();

        // Write the information necessary for decoding into a file
        ObjectOutputStream treeOutStream
            = new ObjectOutputStream(FileOutputStream(treefile));
        outStream.writeInt(bytesAtOnce);
        outStream.writeInt(minCodeSize);
        outStream.writeInt(maxCodeSize);
        outStream.writeObject(byteCombFor);
        outStream.close();
    }

    /** Decodes the data encoded using Huffman's method in {@code infileName}
     * with the information (Huffman tree equivalent) in {@code treefile} and
     * stores the result in {@code outfile}.
     */
    public static void decode(String infileName, String outfile, String
                              treefile) {
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
        int bytesAtOnce = treefile.readInt();
        int minCodeSize = treefile.readInt();
        int maxCodeSize = treefile.readInt();
        Map<BitSet, Integer> byteCombFor
            = (Map<BitSet, Integer> treefile.readObject();
        treeInStream.close();

        // Read the whole encoded data into a bit vector
        File infile          = new File(infileName);
        InputStream inStream = new FileInputStream(infile);
        byte encodedBytes[infile.length()];
        inStream.read(encodedBytes);
        BitSet encoded = new BitSet.valueOf(encodedBytes);
        inStream.close();

        // Open the output file
        OutputStream outStream
            = new BufferedOutputStream(new FileOutputStream(outfile));

        // Go through the BitSet
        int bitSetOffset = 0;
        CODE:
        while (1) {
            // Try and read the code for one byte combination
            for (int codelen = minCodeSize; codelen <= maxCodeSize; ++codelen)
                    {
                // Look up codelen bits
                BitSet potentialCode
                    = encoded.get(bitSetOffset, bitSetOffset + codelen);
                Integer byteComb = byteCombFor.get(potentialCode) {

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
            Map<Integer, BitSet> codeFor, boolean value) {
        // Go through the byte combinations
        for (Integer byteComb : byteCombs) {
            // Add a new empty code for a combination that hadn't been used
            if (! codeFor.containsKey(byteComb)) {
                codeFor.put(byteComb, new BitSet());
            }

            // Set the lowest bit to the desired value
            BitSet curCode = codeFor.get(byteComb);
            curCode.set(curCode.size(), value);
        }
    }

    /**
     * Reads {@code count} (at most four) bytes from {@code inStream} and
     * packs them into an int. Returns -1 if there is no more to read.
     */
    private static int readN(int count, InputStream inStream) {
        assert(count > 0);
        assert(count < 5);

        int res = 0;
        byte buffer[int];

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
    private static void writeN(int count, int bytes, OutputStream outStream) {
        assert(count > 0);
        assert(count < 5);

        byte buffer[count]

        // Transform the int into an array of bytes
        for (int i = 0; i < count; ++i) {
            buffer[count - i - 1] = bytes & (0xff << i);
        }

        outStream.write(buffer);
    }
}
