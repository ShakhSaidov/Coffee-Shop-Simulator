/**
 * Node class that stores the current node with its element, and the next node, it has getters for the elements
 * and setters for both the element and the next Node
 * @author Shakh Saidov
 */
public class Node<T> {
    private Node<T> next;
    private T element;

    public Node() {
        next = null;
        element = null;
    }

    public Node(T element) {
        next = null;
        this.element = element;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> node) {
        next = node;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }
}