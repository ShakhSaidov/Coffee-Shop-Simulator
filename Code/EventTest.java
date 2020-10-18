import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class EventTest.
 *
 * @author  Shakh Saidov
 */
public class EventTest{
    @Test
    /**
     * Tests methods concerning an event
     */
    public void testEvent(){
        Event arrival = new Event(Event.CUSTOMER_ARRIVAL, new Customer(21660, 180, 7.50), 21660);
        assertEquals(21660, arrival.getTime());         //checking getTime()
        
        Event wait = new Event(Event.CUSTOMER_WAIT, new Customer(21720, 180, 7.50), 21660);
        Customer cust = (Customer) wait.getTarget();
        assertEquals(180, cust.getServiceTime());               //to check that we retrieved the correct customer from event
        
        Cashier c = new Cashier();
        c.assignCustomer(new Customer(21720, 180, 7.50));
        Event finish = new Event(Event.SERVICE_FINISH, c, 21900);
        assertEquals(Event.SERVICE_FINISH, finish.getType());   //checking getType()
        
        int result = arrival.compareTo(finish);
        boolean compared;
        if (result < 0){
            compared = true;
        } else{
            compared = false;
        }
        assertTrue(compared);                       //checking if Comparable is correctly implemented
    }
}
