package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

public class SimpleNodeTest {
    private static final int testint0 = 20;
    private static final int testint1 = -45;
    private Node<Integer> node0;
    private Node<Integer> node1;

    @Before public void setUp() {
        node0 = new SimpleNode<Integer>(testint0);
        node1 = new SimpleNode<Integer>(testint0);
    }

    @Test public void testGet() {
        assertEquals(testint0, (int) node0.getValue());
    }

    @Test public void testSetGet() {
        node0.setValue(testint1);
        assertEquals(testint1, (int) node0.getValue());
    }
}
