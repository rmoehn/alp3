package alp3.ueb;

/**
 * A simple node in a graph.
 */
public class SimpleNode<T> implements Node<T> {
    private T value;

    /*
     * Hidden default constructor.
     */
    private SimpleNode() { }

    /**
     * Creates a new node with the specified value.
     */
    public SimpleNode(T value) {
        this.value = value;
    }

    /**
     * Sets the value of this node to the specified value.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Returns the value of this node.
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns a {@link String} representing this node.
     */
    @Override public String toString() {
        return getValue().toString();
    }
}
