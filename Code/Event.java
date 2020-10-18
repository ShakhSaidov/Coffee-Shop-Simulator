/**
 * An Event class stores an event -> a time point when something changes in the simulation.
 * It has 3 types:
 * - when customer arrives
 * - when customer waits to be served
 * - when customer has been serviced and leaves
 * It also implements Comparable so the priority queue that will be made can be sorted time-wise
 * 
 * @author Shakh Saidov
 */
public class Event implements Comparable<Event> {
    public static final int CUSTOMER_ARRIVAL = 0;
    public static final int CUSTOMER_WAIT = 1;
    public static final int SERVICE_FINISH = 2;

    private int time;         // The time that this event happens
    private int type;         // The type of this event (arrival, waiting, or serviced)
    private Object target;    // Either a Customer (if type is arrival/waiting) or a Cashier (if type is serviced)

    public Event(int type, Object target, int time){
        this.type = type;
        this.target = target;
        this.time=time;
    }

    /**
     * Gets the target of the event (Customer or Cashier)
     * @return Object target person
     */
    public Object getTarget(){
        return target;
    }

    /**
     * Gets the type of event 
     * @return int type
     */
    public int getType(){
        return type;
    }

    /**
     * Gets the time of the event
     * @return int time
     */
    public int getTime(){
        return time;
    }

    /**
     * To implement Comparable, compares events based on their time
     * @param Object to be compare
     */
    public int compareTo(Event e){
        return (time - e.getTime());
    }
}
