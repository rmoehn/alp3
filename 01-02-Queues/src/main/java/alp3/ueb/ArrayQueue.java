package alp3.ueb;

/**
 * A simple queue.   The underlying data structure is an array.
 * When it is full, all elements are copied into a larger array. So think
 * whether this approach suits you best.
 */
public class ArrayQueue<E> implements Queue<E> {
    /*
     * There is an index pointing at the head (first element to be removed) of
     * the queue and one pointing at the tail (cell where new elements are
     * placed). If there is a new element to be inserted into the queue and
     * the tail index is pointing at the last index of the array, the element
     * is inserted at the beginning of the array, if there are no elements
     * (wrap-around).
     */
    private static final int INITIAL_SIZE = 20;
    private int queue_size = 0;
    private int head_ind = 0;
    private int tail_ind = 0;

    // The underlying array
    private E[] queue_array;

    /**
     * Constructs a new empty <code>ArrayQueue</code>.
     */
    public ArrayQueue() {
        queue_array = (E[]) new Object[INITIAL_SIZE];
            // Zu faul für weiteren Konstruktor mit wählbarer Größe.
            // Aber wie macht man das richtig und sicher?
    }

    /**
     * Checks whether this <code>ArrayQueue</code> is empty.
     *
     * @return <code>true</code> if this ArrayQueue is empty
     *         <code>false</code> otherwise
     */
    public boolean isEmpty() {
        /*
         * This ArrayQueue is empty if head index and tail index are the
         * same.
         */

        return head_ind == tail_ind;
    }

    /**
     * Checks whether this <code>ArrayQueue</code>'s underlying array is
     * full.
     *
     * @return <code>true</code> if this ArrayQueue's underlying array is
     *                           full
     *         <code>false</code> otherwise
     */
    private boolean is_full() {
        /*
         * To distinguish between the array being full and being empty, we
         * don't put more than length-1 elements into it. Thus, when the tail
         * index points at the field before the head index, the array is full.
         */

        return (head_ind == tail_ind + 1)
            || (head_ind == 0 && tail_ind == queue_array.length - 1);
    }

    /**
     * Puts a new element at the tail of this <code>ArrayQueue</code>.
     *
     * @param new_elem   element to be put at the tail of this ArrayQueue
     */
    public void enqueue(E new_elem) {
        // Expand the underlying array if it is full
        if (this.is_full()) {
            this.expand_queue_array();
        }

        // Insert the new element at the tail
        queue_array[tail_ind] = new_elem;

        // Set tail to new position
        if (tail_ind < queue_array.length - 1) {
            ++tail_ind;
        }
        else {
            tail_ind = 0;
        }

        // Increment the size of the queue
        ++queue_size;
    }

    /**
     * Returns the element at the head of this <code>ArrayQueue</code>.
     * This does not remove it, though.
     *
     * @return element at the head of this ArrayQueue
     *
     * @throws EmptyQueueException if this ArrayQueue is empty
     */
    public E head() throws EmptyQueueException {
        if (! this.isEmpty()) {
            return queue_array[head_ind];
        }
        else {
            throw new EmptyQueueException(
                          "Can't return the head of an empty queue.");
        }
    }

    /**
     * Removes the element at the head of this <code>ArrayQueue</code> and
     * returns it.
     *
     * @return element at the head of this ArrayQueue
     *
     * @throws EmptyQueueException if this ArrayQueue is empty
     */
    public E dequeue() throws EmptyQueueException {
        E head_elem;

        // Retrieve element from the head of this ArrayQueue
        head_elem = this.head();

        // Move along the head index, thus deleting the element at the head
        if (head_ind < queue_array.length - 1) {
            ++head_ind;
        }
        else {
            head_ind = 0;
        }

        // Decrement the queue size
        --queue_size;

        return head_elem;
    }

    /**
     * Creates a String representation of this <code>ArrayQueue</code>.
     *
     * @return a String representing this ArrayQueue
     */
    public String toString() {
        int i = head_ind;
        String outstring = "(";

        if (! this.isEmpty()) {
            // If queue is not wrapped around inside the array
            if (head_ind < tail_ind) {
                // Write elements into string, one after another
                for (i = head_ind; i <= tail_ind - 2; ++i) {
                    outstring += queue_array[i] + ", ";
                }
            }
            // If queue is wrapped around
            else if (tail_ind != 0) { // would be a problem without checking
                // Write elements from head index to end of array
                for (i = head_ind; i <= queue_array.length - 1; ++i) {
                    outstring += queue_array[i] + ", ";
                }

                // Write elements from beginning of array to tail index
                for (i = 0; i <= tail_ind - 2; ++i) {
                    outstring += queue_array[i] + ", ";
                }
            }

            // Write the last element
            outstring += queue_array[i];
                // i is tail_ind - 1 here or head_ind
        }

        // Add closing bracket
        outstring += ")";

        return outstring;
    }

    /**
     * Expands this <code>ArrayQueue</code>'s underlying array.   This
     * creates a new underlying array twice the size of the old one and copies
     * the elements from the old array into the initial part of the new one.
     * The indices are reset accordingly.
     */
    private void expand_queue_array() {
        int n_ind = 0;

        // Create new array twice the size of the old one
        E[] new_array
            = (E[]) new Object[2 * queue_array.length];

        // If the queue is not wrapped around inside the array
        if (head_ind < tail_ind) {
            // Just copy it into the initial part of the new one
            for (int i = head_ind; i < tail_ind; ++i, ++n_ind) {
                new_array[n_ind] = queue_array[i];
            }
        }
        // If it is wrapped around
        else {
            // Copy the first part into the new array
            for (int i = head_ind; i <= queue_array.length - 1;
                    ++i, ++n_ind) {
                new_array[n_ind] = queue_array[i];
            }

            // Copy the second part into the new array after that
            for (int i = 0; i < tail_ind; ++i, ++n_ind) {
                new_array[n_ind] = queue_array[i];
            }
        }

        // Reset the indices
        head_ind = 0;
        tail_ind = n_ind; // already incremented

        // Replace the old array
        queue_array = new_array;
    }

    public int size() {
        return queue_size;
    }
}
