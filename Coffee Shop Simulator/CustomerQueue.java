/**
 * CustomerQueue class that makes the queue using nodes and handles it in a FIFO basis.
 * @author Shakh Saidov
 */
public class CustomerQueue <T> implements Queue <T>{
    private Node <T> front;
    private Node <T> back;
    private int count;

    public CustomerQueue() {
        front = null;
        back = null;
        count = 0;
    }
    
    /**
     * Adds the Customer to the back, folling FIFO rules
     * @param T element
     */
    public void add(T element) {
        Node <T> node = new Node<T>(element);
        if (front == null) { //Empty list
            front = node;
            back = node;
        } else {
            back.setNext(node);
            back = node;
        }
        count++;
    }
    
    /**
     * Returns the first Customer in queue, following FIFO rules
     * @return T element
     */
    public T remove() {
        if (isEmpty()) {
            return null;
        }
        
        T temp = front.getElement();
        front = front.getNext();
        
        if (front == null){
            back = null;
        }
        
        count--;
        return temp;
    }
    
    /**
     * Returns but doesn't remove the first Customer
     * @return T element
     */
    public T peek() {
        if (isEmpty()){
            return null;
        }
        
        return front.getElement();
    }

    /**
     * Checks if the queue is empty
     * @return true if empty
     * @return false if not empty
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the size of the queue
     * @return int size
     */
    public int size() {
        return count;
    }
}
