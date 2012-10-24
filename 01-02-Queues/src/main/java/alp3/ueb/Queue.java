package alp3.ueb;

public interface Queue<E> {
    public int size();
    public void enqueue(E element);
    public E dequeue() throws EmptyQueueException;
    public boolean isEmpty();
}
