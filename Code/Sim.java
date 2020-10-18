import java.util.*;
/**
 * Sim is an event driven simulation that simulates a certain number of cashiers that service customers 
 * that come to the shop from 06:00 - 21:00.
 * 
 * The customers are created by a CustomerQueue that stores the customers.
 * There are 3 complementary lists that receive customers based on their state.
 * - serviceCustomers (ArrayList, because we only need it's size)
 * - overflownCustomers (ArrayList, because we only need it's size)
 * - wiatedCustomers (LinkedList because we come back to it and iterate over it to get data like avgWaitTime and MaxWaitTime
 * 
 * Cashiers are also in a linkedList because iteration is needed to check availability
 * If cashiers are busy, customers' events are labelled as "waiting', if the size hits 24, 
 * customers leave and are added to 'overflown' list
 * 
 * Events are stored in a priorityQueue
 * 
 * @author Shakh Saidov
 */
public class Sim {
    private CustomerQueue<Customer> customers;          //Queue of customers
    private LinkedList<Cashier> cashiers;               //Cashiers list
    private PriorityQueue<Event> eventQueue;            //Event priority queue

    private ArrayList<Customer> servicedCustomers;      // The serviced customers
    private ArrayList<Customer> overflownCustomers;     // The overflown customers
    private LinkedList<Customer> waitedCustomers;       // The customers that waited

    private int waitLineSizeLimit = 0;                  //initial waiting line size (later will be numCashiers * 8)
    private int waitLineSize;                           //The wait line at the time of an event
    private int currentTime;                            //Current real time
    private double cashierWage;                         //the wage of cashiers, indicated in input.txt

    public Sim() {
        customers = new CustomerQueue<Customer>();
        cashiers = new LinkedList<Cashier>();
        eventQueue = new PriorityQueue<Event>();
        waitedCustomers = new LinkedList<Customer>();
        servicedCustomers = new ArrayList<Customer>();
        overflownCustomers = new ArrayList<Customer>();
        currentTime = 21600;            //starts 06;00;00
        waitLineSize = 0;
    }

    /**
     * Adds a customer to the CustomerQueue
     * @param Customer c
     */
    public void addCustomer(Customer c) {
        customers.add(c);
    }

    /**
     * Adds a cashier to the CashierList
     * Modifies the waitLineSizeLimit (adds 8 per each cashier)
     * @param Cashier c
     */
    public void addCashier(Cashier c) {
        cashiers.add(c);
        waitLineSizeLimit += 8;
    }

    /**
     * Sets the wage of cashiers
     * @param double cW
     */
    public void setCashierWage(double cW) {
        cashierWage = cW;
    }

    /**
     * Gets the number of cashiers
     * @return int size
     */
    public int getCashiersNum() {
        return cashiers.size();
    }

    /**
     * Gets the number of served customers
     * @return int size
     */
    public int getServedCSize() {
        return servicedCustomers.size();
    }

    /**
     * Gets the number of overflown customers
     * @return int size
     */
    public int getOverflownCSize() {
        return overflownCustomers.size();
    }

    /**
     * Gets the number of waited customers
     * @return int size
     */
    public int getWaitedCSize() {
        return waitedCustomers.size();
    }

    /**
     * Runs the simulation, with all the data added (Cashiers and their wage, Customers)
     */
    public void run() {
        setupQ();
        currentTime = 0;

        // Process events until there is no left
        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.remove();
            currentTime = event.getTime();          //current real time adjusted
            handleEvent(event);
        }
    }

    /**
     * Sets up the event priorityQueue by adding all customers as they "arrive", till the CustomerQueue is empty
     */
    public void setupQ() {
        while (!customers.isEmpty()) {
            Customer customer = customers.remove();
            Event event = new Event(Event.CUSTOMER_ARRIVAL, customer, customer.getArrivalTime());
            eventQueue.add(event);
        }
    }

    /**
     * Handles the event accordingly
     * 1) If customer arrives, assign it to cashier
     * 2) If cashiers are busy, make the customer wait
     * 3) If the waiting customers are = waitLineSizeLimit, the next customer is overflown
     * 4) If the customer has been serviced, makes the cashier available
     * 
     * @param Event event
     */
    public void handleEvent(Event event){
        // a new customer arrives and a cashier is available
        if (event.getType() == Event.CUSTOMER_ARRIVAL && findAvailableCashier() != null){ 
            Cashier cashier = findAvailableCashier();                   //finds available cashier
            cashier.assignCustomer((Customer) event.getTarget());       //assigns customer to cashier
            eventQueue.add(cashier.getFinishedEvent(currentTime));      //Adds an event with the finished time to the pQueue
        } 
        // the waiting customer sees an available cashier and gets serviced (Same as above but the waitLineSize is adjusted)
        else if (event.getType() == Event.CUSTOMER_WAIT && findAvailableCashier() != null) { 
            Cashier cashier = findAvailableCashier();                   //finds available cashier
            cashier.assignCustomer((Customer) event.getTarget());       //assigns customer to cashier
            eventQueue.add(cashier.getFinishedEvent(currentTime));      //Adds an event with the finished time to the pQueue
            waitLineSize--;                                             //One less customer waiting, so size reduced
        } 
        //The customer has been served and is ready to leave
        else if (event.getType() == Event.SERVICE_FINISH) {
            Cashier cashier = (Cashier) event.getTarget();              //Gets the cashier that finished serving
            cashier.setAvailable(true);                                 // Cashier is now available

            Customer customer = cashier.getCustomer();                  //Gets the customer that has been serviced
            customer.setDepartureTime(currentTime);                     //The time customer left is adjusted
            servicedCustomers.add(customer);                            //Added to the servicedCustomers list
        } 
        //There are no cashiers available, customer waits or leaves
        else if(findAvailableCashier() == null){ 
            if(waitLineSize < waitLineSizeLimit){                      //If the limit hasn't been reached
                int startServeTime = firstFreeCashierTime();            //The earliest time a cashier will be available
                Customer c = (Customer) event.getTarget();              //Customer grabbed
                eventQueue.add(new Event(Event.CUSTOMER_WAIT, c, startServeTime));          //New event added   
                c.setWaitTime(startServeTime - currentTime);            //The wait time is calculated
                waitedCustomers.add(c);                                 //Customer is added to the waitedCustomer list
                waitLineSize++;                                         //One more customer has been added to wait line
            } else {                                                    //If the wait line is full
                Customer overflownC = (Customer) event.getTarget();     //Grab the customer
                overflownCustomers.add(overflownC);                     //Add to the overflown list
            }
        } 
    }

    /**
     * Finds if a cashier is available
     * @return Cashier cashier if availably, null otherwise
     */
    public Cashier findAvailableCashier() {
        Iterator<Cashier> it = cashiers.iterator();
        while (it.hasNext()) {
            Cashier cashier = it.next();
            if (cashier.isAvailable()){
                return cashier;
            }
        }
        return null;
    }

    /**
     * Calculates the earliest time a cashier will be ready
     * return int time
     */
    public int firstFreeCashierTime(){
        int min = cashiers.get(0).getFinishedTime(currentTime);             //grab first cashier in list
        for(int i = 1; i < cashiers.size(); i++){
            if(min < cashiers.get(i).getFinishedTime(currentTime)){ 
                min = cashiers.get(i).getFinishedTime(currentTime);        //compare who will be earliest
            }
        }

        return min;
    }

    /**
     * Gets the total profit earned by serviced Customers
     * @return double profit
     */
    public double getTotalProfit(){
        double profit = 0;
        for(Customer c: servicedCustomers){
            profit += c.getProfit();            //add all the customers' profit 
        }

        return Math.floor(profit * 100)/100;    //show only up to 2 decimal places
    }

    /**
     * Calculates the total amount paid for cashiers
     * @return double total pay
     */
    public double getCashierPay(){
        return cashiers.size() * cashierWage;
    }

    /**
     * Gets the net profit earned for the day
     * @return double net profit
     */
    public double getNetProfit(){
        double wages = getCashierPay();
        double profit = getTotalProfit();

        return Math.floor((profit - wages) * 100)/100;          //shows only up to 2 decimal places
    }

    /**
     * Calculates the average waiting time of customers from the obtained list
     * @return double avg wait time
     */
    public double getAvgWaitTime(){
        double time = 0;
        Iterator itr = waitedCustomers.iterator();
        while(itr.hasNext()){
            Customer c = (Customer) itr.next();
            time += c.getWaitTime();                    //add the waitTimes of all customers in the list
        }

        return Math.floor((time / waitedCustomers.size()) * 100)/100;       //get the average, up to 2 decimal places
    }

    /**
     * Get the maximum wait time a customer had to wait for the day
     * @return double max wait time
     */
    public double getMaxWaitTime(){
        double maxTime = 0;
        Iterator itr = waitedCustomers.iterator();
        while(itr.hasNext()){
            Customer c = (Customer) itr.next();
            if(c.getWaitTime() > maxTime){
                maxTime = c.getWaitTime();          //gets the maximum wait time
            }
        }

        return maxTime;
    }
}
