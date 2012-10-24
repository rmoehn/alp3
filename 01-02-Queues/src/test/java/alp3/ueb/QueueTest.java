package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Random;

/**
 * Class for testing the class <code>ArrayQueue</code>.
 */
public abstract class QueueTest {
    private static final int TESTNUMS_CNT = 500;

    public Integer[] testnums = new Integer[TESTNUMS_CNT];
    public Queue<Integer> queue;

    public QueueTest() {
        super();
    }

    /**
     * Generates an array of test objects for the queue.
     */
    @Before public void setUp() {
        Random random = new Random();
        for (int i = 0; i < TESTNUMS_CNT; ++i) {
            testnums[i] = new Integer(random.nextInt());
        }

        queue = createQueue();
    }

    public abstract Queue<Integer> createQueue();

    // Test basic operations -- difficult to test separately
    @Test public void test_basic() throws EmptyQueueException {
        assertTrue(queue.isEmpty());

        // Some simple stuff
        queue.enqueue(testnums[0]);
        assertTrue(!queue.isEmpty());
        assertEquals(1, queue.size());

        assertEquals(queue.dequeue(), testnums[0]);
        assertTrue(queue.isEmpty());

        // Some random operations
        queue.enqueue(testnums[1]);
        queue.enqueue(testnums[2]);
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
        for (int i = 0; i < TESTNUMS_CNT; ++i) {
            queue.enqueue(testnums[i]);
            assertTrue(!queue.isEmpty());
        }
        assertEquals(TESTNUMS_CNT, queue.size());
        for (int i = 0; i < TESTNUMS_CNT; ++i) {
            assertTrue(!queue.isEmpty());
            assertEquals(queue.dequeue(), testnums[i]);
        }
        assertTrue(queue.isEmpty());

        // Same procedure but probably somewhere in the middle of the array
        // Or not?
        for (int i = 0; i < TESTNUMS_CNT; ++i) {
            queue.enqueue(testnums[i]);
            assertTrue(!queue.isEmpty());
        }
        assertEquals(TESTNUMS_CNT, queue.size());
        for (int i = 0; i < TESTNUMS_CNT; ++i) {
            assertTrue(!queue.isEmpty());
            assertEquals(queue.dequeue(), testnums[i]);
        }
        assertTrue(queue.isEmpty());
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
