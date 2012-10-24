package alp3.ueb;

import java.util.NoSuchElementException;

/**
 * Provides an <code>EmptyQueueException</code> object.
 */
public class EmptyQueueException extends NoSuchElementException {
    /**
     * Message explaining details of the Exception.
     */
    public String message;

    /**
     * Class constructor with a customised message.
     *
     * @param message a String explaining details of the exception
     */
    public EmptyQueueException(String message) {
        super(message);
    }

    /**
     * Class constructor with default message.
     */
    public EmptyQueueException() {
        this("Illegal action on empty queue performed.");
    }
}
