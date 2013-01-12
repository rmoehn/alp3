package alp3.ueb;

import java.util.Scanner;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
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

    /**
     * Performs DFS on the specified graph starting at the specified node. The
     * return value is the DFS tree. Additionally a rather strange
     * representation of the DFS tree is printed to standard output. If the
     * graph consists of more than one compound, only the DFS tree of the
     * compound to which the specified node belongs is returned and printed.
     */
    public static <T> Graph<T> performDFS(Graph<T> graph, Node<T> startNode) {
        /*
         * This implementation emulates recursion by handling the call stack
         * manually. An additional set allows testing whether a node is on the
         * stack already and thus should not be added to it again.
         */
        Collection<Node<T>> hasBeenSeen = new HashSet<Node<T>>();
        Stack<Node<T>> toBeProcessed    = new Stack<Node<T>>();
        Graph<T> dfsGraph               = new SimpleUndirGraph<T>();

        // Put the start node on the stack, into the dictionary and the tree
        hasBeenSeen.add(startNode);
        toBeProcessed.push(startNode);
        dfsGraph.addNode(startNode);

        // And print it
        System.out.println(startNode.toString());

        // While we haven't examined all nodes for neighbours
        STACK:
        while (!toBeProcessed.empty()) {
            // Find all neighbours of one of the remaining nodes
            Node<T> curNode              = toBeProcessed.peek();
            Collection<Node<T>> adjNodes = graph.getAdjacentNodes(curNode);

            // Look through those neighbours...
            for (Node<T> adjNode : adjNodes) {
                // ...until we find one that hasn't been seen yet
                if (! hasBeenSeen.contains(adjNode)) {
                    // Add it to the list of nodes to be examined
                    toBeProcessed.push(adjNode);
                    hasBeenSeen.add(adjNode);

                    // Print this node at its proper depth in the tree
                    System.out.println(
                        multiplyString("\t", toBeProcessed.size() - 1)
                        + adjNode.toString()
                    );

                    // Add an edge to its predecessor in the DFS tree
                    dfsGraph.addNode(adjNode);
                    dfsGraph.addEdge(adjNode, curNode);

                    // Look for a neighbour of the newly added node (DF!)
                    continue STACK;
                }
            }

            // The node on top of the stack had no more neighbours
            toBeProcessed.pop();
        }

        return dfsGraph;
    }

    /**
     * Returns a {@code String} consisting of {@code n} repetitions of the
     * specified string.
     */
    private static String multiplyString(String inString, int n) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < n; ++i) {
            builder.append(inString);
        }

        return builder.toString();
    }


}
