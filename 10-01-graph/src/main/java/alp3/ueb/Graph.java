package alp3.ueb;

import java.util.Collection;

interface Graph<T> {
    Collection<Node<T>> getNodes();
    Collection<Node<T>> getAdjacentNodes(Node<T> node);
    boolean areAdjacent(Node<T> node1, Node<T> node2);
    void addNode(Node<T> node);
    void removeNode(Node<T> node);
    void addEdge(Node<T> node1, Node<T> node2);
    void removeEdge(Node<T> node1, Node<T> node2);
}
