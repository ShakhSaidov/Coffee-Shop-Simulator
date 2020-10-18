/**
 * Cashier class stores a cashier and contains methods needed for the cashier's tasks
 * @author Shakh Saidov
 */
public class Cashier {
    private Customer currentCustomer; 
    private boolean available;        

    public Cashier(){
        currentCustomer = null;
        available = true;
    }

    /**
     * Assigns a customer to the current cashier
     * @param Customer 
     */
    public void assignCustomer(Customer customer){
        currentCustomer = customer;
        available = false;
    }

    /**
     * Returns the current customer that's being served by the cashier
     * @return Current customer
     */
    public Customer getCustomer(){
        return currentCustomer;
    }

    /**
     * Gets the time the cashier will finish serving current customer and will be free
     * @param int current time
     * @return int finishTime
     */
    public int getFinishedTime(int currentTime){
        return currentTime + currentCustomer.getServiceTime();
    }

    /**
     * Makes an event of the cashier finishing service to the customer, to put it onto the event priority queue
     * @param int current time
     * @return Event of finished service
     */
    public Event getFinishedEvent(int currentTime){
        return new Event(Event.SERVICE_FINISH, this, currentTime + currentCustomer.getServiceTime());
    }

    /**
     * Checks if the cashier is assigned to a customer or no.
     * @return true if cashier available
     * @return false if cashier busy
     */
    public boolean isAvailable(){
        return available;
    }

    /**
     * Sets the avaliablity of the cashier
     * @param boolean available
     */
    public void setAvailable(boolean available){
        this.available = available;
    }
}
