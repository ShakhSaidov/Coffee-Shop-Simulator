import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
/**
 * The test class CustomerTest.
 *
 * @author  Shakh Saidov
 * @version (a version number or a date)
 */
public class CustomerTest{
    @Test
    /**
     * Tests all the methods concerning a customer
     */
    public void testCustomer(){
        Customer test = new Customer(21605, 30, 12.35);    //arrives at 06:00:05 seconds, takes 30 seconds to serve, pays 15 dollars
        test.setWaitTime(10);                       //customer waits for 10 seconds
        test.setDepartureTime(21645);               //customer will leave at 06:00:45
        
        assertEquals(21605, test.getArrivalTime());
        assertEquals(30, test.getServiceTime());
        assertEquals(12.35, test.getProfit(), 0.0);
        assertEquals(10, test.getWaitTime());
        assertEquals(21645, test.getDepartureTime());
    }
}
