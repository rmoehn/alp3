package alp3.ueb;

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
}
