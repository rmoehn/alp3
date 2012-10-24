package alp3.ueb;

/**
 * Class for testing the class <code>ArrayQueue</code>.
 */
public class LinkedListQueueTest extends QueueTest {
    public LinkedListQueueTest() {
        super();
    }

    public Queue<Integer> createQueue() {
        return new LinkedListQueue<Integer>();
    }
}
