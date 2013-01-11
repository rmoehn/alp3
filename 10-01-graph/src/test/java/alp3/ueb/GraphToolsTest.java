package alp3.ueb;

import java.util.Iterator;
import java.util.Collection;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.FileInputStream;

import org.junit.*;
import static org.junit.Assert.*;

// Very ugly test class.

// Awful test class, indeed.

public class GraphToolsTest {
    private static final int[] nodes = { 1, 3, 4, 5, 7, 8, 23 };
    private static final int[][] adjacentFor = {
        {},
        { 3 },
        {},
        { 1, 4, 5, 7 },
        { 3, 5 },
        { 3, 4, 8, 23 },
        {},
        { 3 },
        { 5 },
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        { 5 },
    };
    private Graph<Integer> testgraph;
    private Collection<Node<Integer>> readNodes;

    @Before public void setUp() throws IOException, FileNotFoundException {
        // Write some graph to some file
        File graphFile       = File.createTempFile("graph", "tmp");
        PrintWriter graphOut = new PrintWriter(graphFile);
        graphOut.println("1 3\n3 7\n4 3\n5 3\n4 5\n5 8\n23 5");
        graphOut.close();
        assert(!graphOut.checkError());

        // Make a graph from it
        InputStream graphIn = new FileInputStream(graphFile);
        testgraph = GraphTools.connUndirSimpIntGraphFromStream(graphIn);
        readNodes = testgraph.getNodes();
    }

    // Make sure all nodes (and only those) are present
    @Test public void testNodes() {
        assertEquals(nodes.length, readNodes.size());

        Collection<Integer> readInts = nodesToIntColl(readNodes);
        for (int node : nodes) {
            assertTrue(readInts.contains(node));
        }
    }

    // Make sure all edges are where they should be
    @Test public void testEdges() {
        for (Node<Integer> node : readNodes) {
            Collection<Integer> adjacent
                = nodesToIntColl(testgraph.getAdjacentNodes(node));

            int[] shouldBeAdjacent = adjacentFor[node.getValue()];
            assertEquals(shouldBeAdjacent.length, adjacent.size());
            for (int adj : shouldBeAdjacent) {
                assertTrue(adjacent.contains(adj));
            }
        }
    }

    // Make sure DFS is performed properly (ocular inspection)
    @Test public void testDFS() {
        Graph<Integer> dfsGraph
            = GraphTools.performDFS(testgraph, readNodes.iterator().next());
        assertEquals(nodes.length, dfsGraph.getNodes().size());
    }

    // Returns a Collection of Integers from a Collection of Nodes of
    // Integers.
    private static Collection<Integer> nodesToIntColl(
            Collection<Node<Integer>> nodes) {
        Collection<Integer> ints = new HashSet<Integer>();

        for (Node<Integer> node : nodes) {
            ints.add(node.getValue());
        }

        return ints;
    }
}
