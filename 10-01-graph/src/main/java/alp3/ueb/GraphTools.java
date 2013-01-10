package alp3.ueb;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;
import java.io.FileInputStream;

/**
 * Various tools for dealing with graphs.
 */
public class GraphTools {
    /**
     * Returns a connected undirected simple graph with {@code Integer}s as
     * {@link Node} values from some {@link InputStream}. The {@code
     * InputStream} has to contain pairs of integers representing edges like
     * these:
     *
     *  1 3
     *  4 3
     *  5 23
     *  3 5
     *
     *  There must be at least two nodes in the graph. Every {@code
     *  InputStream} not complying to these rules may cause unforeseeable
     *  results.
     */
    public static Graph<Integer> connUndirSimpIntGraphFromStream(
            InputStream graphIn) {
        Scanner edges      = new Scanner(graphIn);
        Graph<Integer> res = new SimpleUndirGraph<Integer>();
        Map<Integer, Node<Integer>> nodeFor
            = new HashMap<Integer, Node<Integer>>();

        // As long as there are pairs in the input
        while (edges.hasNextInt()) {
            Integer int1 = edges.nextInt();
            Integer int2 = edges.nextInt();
            Node<Integer> node1;
            Node<Integer> node2;

            // Create two nodes from it or use existing nodes with this value
            if (nodeFor.containsKey(int1)) {
                node1 = nodeFor.get(int1);
            }
            else {
                node1 = new SimpleNode<Integer>(int1);
                nodeFor.put(int1, node1);
            }

            if (nodeFor.containsKey(int2)) {
                node2 = nodeFor.get(int2);
            }
            else {
                node2 = new SimpleNode<Integer>(int2);
                nodeFor.put(int2, node2);
            }

            // Add them to the graph and connect them with an edge
            res.addNode(node1);
            res.addNode(node2);
            res.addEdge(node1, node2);
        }

        return res;
    }
}
