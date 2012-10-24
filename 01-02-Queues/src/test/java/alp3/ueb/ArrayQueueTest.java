package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Random;

/**
 * Class for testing the class <code>ArrayQueue</code>.
 */
public class ArrayQueueTest {
    public int testnums_cnt = 500;

    public Integer[] testnums = new Integer[testnums_cnt];

    @Before public void setUp() {
        Random random = new Random();
        for (int i = 0; i < testnums_cnt; ++i) {
            testnums[i] = new Integer(random.nextInt());
        }
    }

    // Test basic operations -- difficult to test separately
    @Test public void test_basic() throws EmptyQueueException {
        ArrayQueue<Integer> queue = new ArrayQueue<Integer>();

        assertTrue(queue.isEmpty());

        // Some simple stuff
        queue.enqueue(testnums[0]);

        assertEquals(queue.head(), testnums[0]);
        assertTrue(!queue.isEmpty());

        assertEquals(queue.dequeue(), testnums[0]);
        assertTrue(queue.isEmpty());

        // Some random operations
        queue.enqueue(testnums[1]);
        queue.enqueue(testnums[2]);
        assertEquals(queue.head(), testnums[1]);
        queue.enqueue(testnums[3]);
        assertEquals(queue.dequeue(), testnums[1]);
        assertEquals(queue.dequeue(), testnums[2]);
        queue.enqueue(testnums[4]);
        queue.enqueue(testnums[5]);

        assertTrue(! queue.isEmpty());

        assertEquals(queue.dequeue(), testnums[3]);
        assertEquals(queue.dequeue(), testnums[4]);

        assertEquals(queue.dequeue(), testnums[5]);

        assertTrue(queue.isEmpty());

        queue.enqueue(testnums[6]);
        assertEquals(queue.dequeue(), testnums[6]);

        assertTrue(queue.isEmpty());

        // Should also account for the extensibility of the fellow
        for (int i = 0; i < testnums_cnt; ++i) {
            queue.enqueue(testnums[i]);
            assertTrue(!queue.isEmpty());
        }
        for (int i = 0; i < testnums_cnt; ++i) {
            assertTrue(!queue.isEmpty());
            assertEquals(queue.dequeue(), testnums[i]);
        }

        // Same procedure but probably somewhere in the middle of the array
        // Or not?
        for (int i = 0; i < testnums_cnt; ++i) {
            queue.enqueue(testnums[i]);
            assertTrue(!queue.isEmpty());
        }
        for (int i = 0; i < testnums_cnt; ++i) {
            assertTrue(!queue.isEmpty());
            assertEquals(queue.dequeue(), testnums[i]);
        }
        assertTrue(queue.isEmpty());
    }

    // Test exceptions
    @Test(expected=EmptyQueueException.class)
    public void test_exc_1() throws EmptyQueueException {
        ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
        Integer bla = queue.head();
    }

    @Test(expected=EmptyQueueException.class)
    public void test_exc_2() throws EmptyQueueException {
        ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
        Integer bla = queue.dequeue();
    }

    @Test(expected=EmptyQueueException.class)
    public void test_exc_3() throws EmptyQueueException {
        ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
        queue.enqueue(4);
        Integer bla = queue.dequeue();
        bla = queue.dequeue();
    }
}
