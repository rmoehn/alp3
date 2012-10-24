package alp3.ueb;

import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A simple dynamic queue. The underlying data structure is a LinkedList.
 */
class LinkedListQueue<E> implements alp3.ueb.Queue<E> {
    private java.util.Queue<E> internalQueue = new LinkedList<E>();

    /**
     * Creates a new instance of this class.
     */
    public LinkedListQueue() { }

    /**
     * Adds an element to this queue.
     */
    public void enqueue(E e) {
        internalQueue.add(e);
    }

    /**
     * Removes an element from this queue and returns it.
     *
     * @throws EmptyQueueException if the queue is empty
     */
    public E dequeue() throws EmptyQueueException {
            // Don't blame me for declaring unchecked exceptions.
        E head;
        try {
            head = internalQueue.remove();
                // Not using poll here because of semi-predicate problem.
        }
        catch (NoSuchElementException e) {
            throw new EmptyQueueException();
                // Brilliant idea to use custom exceptions.
        }

        return head;
    }

    /**
     * Checks whether this queue is empty.
     */
    public boolean isEmpty() {
        return internalQueue.isEmpty();
    }

    /**
     * Returns the number of elements in this queue.
     */
    public int size() {
        return internalQueue.size();
    }
}
