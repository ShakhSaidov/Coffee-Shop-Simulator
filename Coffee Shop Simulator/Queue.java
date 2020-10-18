/**
 * Queue interface that has 5 methods to handle the nodes in the queue
 * @author Shakh Saidov
 */
public interface Queue<T> {
    public void add(T element); // adds element to the queue

    public T remove(); // removes and returns the head element of queue        

    public T peek(); // returns without removing the head element of queue

    public boolean isEmpty(); // returns true if queue contains no elements 

    public int size(); // returns number of elements in queue
}