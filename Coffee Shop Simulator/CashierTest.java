import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CashierTest.
 *
 * @author  Shakh Saidov
 */
public class CashierTest{
    @Test
    /**
     * Tests the methods concerning a cashier. 
     */
    public void testCashier(){
        Cashier test = new Cashier();
        Customer c = new Customer(21660, 120, 9.50);            //arrives 6:01:00, served for 2 minutes, pays $9.50
        
        test.assignCustomer(c);
        assertFalse(test.isAvailable());                        //cashier is busy now
                      
        assertEquals(21780, test.getFinishedTime(21660));       //cashier will be free in 2 minutes, at 06:03:00
        
        test.setAvailable(true);
        assertTrue(test.isAvailable());                         //now the cashier is free
    }
}
