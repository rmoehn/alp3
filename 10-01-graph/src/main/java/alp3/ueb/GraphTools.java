package alp3.ueb;

import java.util.Scanner;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
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
         * manually. For printing, the depth of the found nodes is recorded in
         * a dictionary. Additionally, when a node is present there, the
         * algorithm knows that it has been seen already and does not add it
         * to the stack once more.
         */
        Map<Node<T>, Integer> depthFor = new HashMap<Node<T>, Integer>();
        Stack<Node<T>> toBeProcessed   = new Stack<Node<T>>();
        Graph<T> dfsGraph              = new SimpleUndirGraph<T>();

        // Put the start node on the stack, into the dictionary and the tree
        depthFor.put(startNode, 0);
        toBeProcessed.push(startNode);
        dfsGraph.addNode(startNode);

        // While we haven't examined all nodes for neighbours
        while (!toBeProcessed.empty()) {
            // Find all neighbours of one of the remaining nodes
            Node<T> curNode              = toBeProcessed.pop();
            int curDepth                 = depthFor.get(curNode);
            Collection<Node<T>> adjNodes = graph.getAdjacentNodes(curNode);

            // Print this node at its proper depth (distance from start node)
            System.out.println(
                multiplyString("\t", curDepth) + curNode.toString()
            );

            // Look at those neighbours, one at a time
            for (Node<T> adjNode : adjNodes) {
                // If it isn't the neighbour of a previously seen node
                if (! depthFor.containsKey(adjNode)) {
                    // Add it to the list of the nodes to be examined
                    toBeProcessed.push(adjNode);
                    depthFor.put(adjNode, curDepth + 1);

                    // Add an edge to its predecessor in the DFS tree
                    dfsGraph.addNode(adjNode);
                    dfsGraph.addEdge(adjNode, curNode);
                }
            }
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
