package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Class for testing the class <code>ArrayQueue</code>.
 */
public class ArrayQueueTest extends QueueTest {
    public ArrayQueueTest() {
        super();
    }

    public Queue<Integer> createQueue() {
        return new ArrayQueue<Integer>();
    }

    /**
     * Basic test for the constructor with capacity specification.
     */
    @Test public void customCapacityTest() {
        Queue<Integer> queue = new ArrayQueue<Integer>(55);
        assertTrue( queue != null );

        Integer testInt = new Integer(2);
        queue.enqueue(testInt);
        assertEquals(testInt, queue.dequeue());
    }
}
