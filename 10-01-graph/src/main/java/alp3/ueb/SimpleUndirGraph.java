package alp3.ueb;

import java.util.Map;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * A simple undirected graph. Simple means that there are no loops or multiple
 * edges.
 */
public class SimpleUndirGraph<T> implements Graph<T> {
    private Map<Node<T>, Collection<Node<T>>> adjacentTo
        = new HashMap<Node<T>, Collection<Node<T>>>();

    /**
     * Creates a new instance of this class.
     */
    public SimpleUndirGraph() { }

    /**
     * Returns a {@link Collection} of all {@link Node}s in this graph.
     */
    public Collection<Node<T>> getNodes() {
        return adjacentTo.keySet();
    }

    /**
     * Returns a {@link Collection} of all {@link Node}s adjacent to the
     * specified node.
     */
    public Collection<Node<T>> getAdjacentNodes(Node<T> node) {
        assertNodeExists(node);
        return adjacentTo.get(node);
    }

    /**
     * Tests whether the specified {@link Node}s are adjacent.
     */
    public boolean areAdjacent(Node<T> node1, Node<T> node2) {
        assertNodeExists(node1);
        assertNodeExists(node2);
        return adjacentTo.get(node1).contains(node2);
    }

    /**
     * Adds the specified {@link Node} to this graph.
     */
    public void addNode(Node<T> node) {
        if (adjacentTo.containsKey(node)) {
            return;
        }
        adjacentTo.put(node, new HashSet<Node<T>>());
    }

    /**
     * Removes the specified {@link Node} from this graph.
     */
    public void removeNode(Node<T> node) {
        assertNodeExists(node);

        // Remove all edges from/to this node
        for (Node<T> adjacentNode : getAdjacentNodes(node)) {
            adjacentTo.get(adjacentNode).remove(node);
        }

        // Remove the node itself
        adjacentTo.remove(node);
    }

    /**
     * Adds an edge between the two specified {@link Node}s in this graph.
     */
    public void addEdge(Node<T> node1, Node<T> node2) {
        assertNodeExists(node1);
        assertNodeExists(node2);

        if (node1 == node2) {
            throw new IllegalArgumentException("Loops not allowed.");
        }

        adjacentTo.get(node1).add(node2);
        adjacentTo.get(node2).add(node1);
    }

    /**
     * Removes the edge between the two specified {@link Node}s in this graph.
     */
    public void removeEdge(Node<T> node1, Node<T> node2) {
        assertNodeExists(node1);
        assertNodeExists(node2);

        // Make sure the edge exists
        if (!adjacentTo.get(node1).contains(node2)
                || !adjacentTo.get(node2).contains(node1)) {
            throw new NoSuchElementException("Specified edge not in graph.");
        }

        // Remove the nodes from the respective adjacency lists
        adjacentTo.get(node1).remove(node2);
        adjacentTo.get(node2).remove(node1);
    }

    /**
     * Throws an exception if the specified node does not belong to this
     * graph.
     */
    private void assertNodeExists(Node<T> node) {
        if (!adjacentTo.containsKey(node)) {
            throw new NoSuchElementException("Specified node not in graph.");
        }
    }
}
