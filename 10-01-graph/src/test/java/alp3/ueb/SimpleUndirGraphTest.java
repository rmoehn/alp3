package alp3.ueb;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.*;
import static org.junit.Assert.*;

public class SimpleUndirGraphTest {
    private static final int int0 = 5;
    private static final int int1 = 65;
    private static final int int2 = -465;
    private Node<Integer> node0;
    private Node<Integer> node1;
    private Node<Integer> node2;
    private Graph<Integer> graph0;
    private Graph<Integer> graph1;
    private Graph<Integer> graph2;
    private Graph<Integer> graph3;

    @Before public void setUp() {
        node0 = new SimpleNode<Integer>(int0);
        node1 = new SimpleNode<Integer>(int1);
        node2 = new SimpleNode<Integer>(int2);

        graph0 = new SimpleUndirGraph<Integer>();

        graph1 = new SimpleUndirGraph<Integer>();
        graph1.addNode(node0);
        graph1.addNode(node1);

        graph2 = new SimpleUndirGraph<Integer>();
        graph2.addNode(node0);
        graph2.addNode(node1);
        graph2.addEdge(node0, node1);

        graph3 = new SimpleUndirGraph<Integer>();
        graph3.addNode(node0);
    }

    // Make sure there are no nodes in a graph to which none were added
    @Test public void testEmpty() {
        Collection<Node<Integer>> nodes = graph0.getNodes();
        assertEquals(0, nodes.size());
    }

    // Make sure we get all nodes in a graph
    @Test public void testGetNodes() {
        Collection<Node<Integer>> nodes = graph1.getNodes();
        assertEquals(2, nodes.size());
        assertTrue(nodes.contains(node0));
        assertTrue(nodes.contains(node1));
    }

    // Make sure we get no adjacent nodes for some node without neighbours
    @Test public void testGetAdjacent0() {
        assertEquals(0, graph3.getAdjacentNodes(node0).size());
    }

    // Make sure we get all adjacent nodes for some node
    @Test public void testGetAdjacent1() {
        Collection<Node<Integer>> nodes = graph2.getAdjacentNodes(node0);
        assertEquals(1, nodes.size());
        assertTrue(nodes.contains(node1));
    }

    // Make sure a node is present after it has been inserted
    @Test public void testAddNode0() {
        graph0.addNode(node0);
        Collection<Node<Integer>> nodes = graph0.getNodes();
        assertEquals(1, nodes.size());
        assertTrue(nodes.contains(node0));
    }

    // Make sure nothing happens when a node is inserted a second time
    @Test public void testAddNode1() {
        graph3.addNode(node0);
        Collection<Node<Integer>> nodes = graph3.getNodes();
        assertEquals(1, nodes.size());
        assertTrue(nodes.contains(node0));
    }

    // Make sure a node is gone after deletion
    @Test public void testRemoveNode0() {
        graph3.removeNode(node0);
        assertEquals(0, graph3.getNodes().size());
    }

    // Make sure nonexistent nodes cannot be deleted
    @Test(expected=NoSuchElementException.class)
    public void testRemoveNode1() {
        graph0.removeNode(node0);
    }

    // Make sure edges are removed on deletion of a node
    @Test public void testRemoveNode2() {
        graph2.removeNode(node0);
        assertEquals(0, graph2.getAdjacentNodes(node1).size());
    }

    // Make sure nodes without an edge in between are not adjacent
    @Test public void testAdjacent0() {
        assertFalse(graph1.areAdjacent(node0, node1));
        assertFalse(graph1.areAdjacent(node1, node0));
    }

    // Make sure nodes with an edge in between are adjacent
    @Test public void testAdjacent1() {
        assertTrue(graph2.areAdjacent(node0, node1));
        assertTrue(graph2.areAdjacent(node1, node0));
    }

    // Make sure a node is not adjacent to itself (simple graph)
    @Test public void testAdjacent2() {
        assertFalse(graph3.areAdjacent(node0, node0));
    }

    // Make sure an exception is thrown when testing for adjacency of
    // nonexistent nodes
    @Test(expected=NoSuchElementException.class)
    public void testAdjacent3() {
        graph3.areAdjacent(node0, node1);
    }

    // Cont.
    @Test(expected=NoSuchElementException.class)
    public void testAdjacent4() {
        graph3.areAdjacent(node1, node0);
    }

    // Make sure an edge is present after it has been added
    @Test public void testAddEdge0() {
        graph1.addEdge(node0, node1);
        assertTrue(graph1.areAdjacent(node0, node1));
        assertTrue(graph1.areAdjacent(node1, node0));
    }

    // Make sure nonexistent nodes cannot be connected
    @Test(expected=NoSuchElementException.class)
    public void testAddEdge1() {
        graph3.addEdge(node0, node1);
    }

    // Cont.
    @Test(expected=NoSuchElementException.class)
    public void testAddEdge2() {
        graph3.addEdge(node1, node0);
    }

    // Make sure an the same node cannot be connected with itself
    @Test(expected=IllegalArgumentException.class)
    public void testAddEdge3() {
        graph3.addEdge(node0, node0);
    }

    // Make sure nothing happens when an edge is inserted a second time
    @Test public void testAddEdge4() {
        graph2.addEdge(node0, node1);
        Collection<Node<Integer>> adj0 = graph2.getAdjacentNodes(node0);
        Collection<Node<Integer>> adj1 = graph2.getAdjacentNodes(node1);

        assertEquals(1, adj0.size());
        assertTrue(adj0.contains(node1));
        assertEquals(1, adj1.size());
        assertTrue(adj1.contains(node0));
    }

    // Cont.
    @Test public void testAddEdge5() {
        graph2.addEdge(node1, node0);
        Collection<Node<Integer>> adj0 = graph2.getAdjacentNodes(node0);
        Collection<Node<Integer>> adj1 = graph2.getAdjacentNodes(node1);

        assertEquals(1, adj0.size());
        assertTrue(adj0.contains(node1));
        assertEquals(1, adj1.size());
        assertTrue(adj1.contains(node0));
    }

    // Make sure nothing happens to the nodes when an edge is added
    @Test public void testAddEdge6() {
        graph1.addEdge(node0, node1);
        Collection<Node<Integer>> nodes = graph1.getNodes();
        assertEquals(2, nodes.size());
        assertTrue(nodes.contains(node0));
        assertTrue(nodes.contains(node1));
    }

    // Make sure a node can have more than one edge
    @Test public void testAddEdge7() {
        graph2.addNode(node0);
        graph2.addNode(node2);
        graph2.addEdge(node0, node2);
        Collection<Node<Integer>> adj = graph2.getAdjacentNodes(node0);
        assertEquals(2, adj.size());
        assertTrue(adj.contains(node1));
        assertTrue(adj.contains(node2));
    }


    // Make sure edges are gone after deletion
    @Test public void testRemoveEdge0() {
        graph2.removeEdge(node0, node1);
        assertFalse(graph2.areAdjacent(node0, node1));
        assertFalse(graph2.areAdjacent(node1, node0));
    }

    // Make sure nonexistent edges cannot be deleted
    @Test(expected=NoSuchElementException.class)
    public void testRemoveEdge1() {
        graph1.removeEdge(node0, node1);
    }

    // Cont.
    @Test(expected=NoSuchElementException.class)
    public void testRemoveEdge2() {
        graph1.removeEdge(node1, node0);
    }
}
