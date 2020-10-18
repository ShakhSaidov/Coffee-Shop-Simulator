/**
 * Customer class manages a customer waiting in line for service
 * @author Shakh Saidov
 */
public class Customer{
    private int arrivalTime;   // arrival time of a customer
    private int waitTime;      // amount of waiting time
    private int departureTime; // departure time 
    private int serviceTime;   // time for clerk to service this customer
    private double profit;     // the money the customer spends

    public Customer(int arrives, int service, double profit) {
        arrivalTime = arrives;
        serviceTime = service;
        this.profit = profit;
        departureTime = 0;         
        waitTime = 0;               
    }

    /**
     * Returns the profit earned from customer
     * @return double profit
     */
    public double getProfit(){
        return profit;
    }

    /**
     * Returns customer arrival time
     * @return int arrival time
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Returns customer waiting-in-line time
     * @return int wait time
     */
    public int getWaitTime() {
        return waitTime;
    }

    /**
     * Returns customer departure time
     * @return int departure time
     */
    public int getDepartureTime() {
        return departureTime;
    }

    /**
     * Returns customer service time
     * @return int service time
     */
    public int getServiceTime() {
        return serviceTime;
    }
    
    /**
     * Sets the time when customer will leave
     * @param int departure time
     */
    public void setDepartureTime(int departs) {
        departureTime = departs;
    }

    /**
     * Sets the amount of time customer will have to wait
     * @param int wait time
     */
    public void setWaitTime(int waits) {
        waitTime = waits;
    }
}