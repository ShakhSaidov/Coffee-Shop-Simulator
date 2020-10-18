import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class SimTest. run(), setupQ() and handleEvent() arent unit tested because they are called inside some of the tests.
 * Since assertEquals works, the abovementioned 3 methods also work properly
 * 
 *
 * @author  Shakh Saidov
 */
public class SimTest{
    @Test
    /**
     * Tests valid findAvailableCashier() case
     */
    public void testCashierNotAvailable() {
        Sim test = new Sim();
        
        Cashier c1 = new Cashier();
        c1.assignCustomer(new Customer(21642, 125, 5.25));
        Cashier c2 = new Cashier();
        c2.assignCustomer(new Customer(21645, 120, 5.12));
        Cashier c3 = new Cashier();
        c3.assignCustomer(new Customer(21694, 130, 5.68));
        
        test.addCashier(c1);
        test.addCashier(c2);
        test.addCashier(c3);
        
        assertNull(test.findAvailableCashier());
    }

    @Test
    /**
     * Tests invalid findAvailableCashier() case
     */
    public void testCashierAvailable() {
        Sim test = new Sim();
        
        Cashier c1 = new Cashier();
        c1.assignCustomer(new Customer(21642, 125, 5.25));
        Cashier c2 = new Cashier();
        c2.assignCustomer(new Customer(21645, 120, 5.12));
        Cashier c3 = new Cashier();
        
        test.addCashier(c1);
        test.addCashier(c2);
        test.addCashier(c3);
        
        assertNotNull(test.findAvailableCashier());
    }
    
    @Test
    /**
     * Testing firstFreeCashierTime()
     */
    public void testCashierFreeTime(){
        Sim test = new Sim();
        
        Cashier c1 = new Cashier();
        c1.assignCustomer(new Customer(21600, 120, 5.25));          
        Cashier c2 = new Cashier();
        c2.assignCustomer(new Customer(21600, 125, 5.12));
        Cashier c3 = new Cashier();
        c3.assignCustomer(new Customer(21600, 130, 5.68));
        
        test.addCashier(c1);
        test.addCashier(c2);
        test.addCashier(c3);
        
        assertEquals(21730, test.firstFreeCashierTime());  
    }

    @Test
    /**
     * Tests getCashierPay(), getTotalProfit() and getNetProfit()
     */
    public void testMoney(){
        Sim test = new Sim();
        
        Cashier c1 = new Cashier();
        Customer cust1 = new Customer(21600, 120, 9.50);
        Customer cust2 = new Customer(21630, 90, 5.00);
        
        test.addCashier(c1);
        test.addCustomer(cust1);
        test.addCustomer(cust2);
        
        test.run();
        
        double profit = 9.50 + 5.00;
        assertEquals(profit, test.getTotalProfit(), 0.00);
        
        test.setCashierWage(10.00);
        assertEquals(10.00, test.getCashierPay(), 0.00);              //there's 1 cashier, so shop pays 10
        
        double net = profit - 10.00;
        assertEquals(net, test.getNetProfit(), 0.00);
    }

    @Test
    /**
     * Tests getAvgWaitTime() and getMaxWaitTime()
     */
    public void testWaiting(){
        Sim test = new Sim();
        
        Cashier c = new Cashier();
        
        Customer cust1 = new Customer(21600, 120, 9.50);    //06;00;00 arrival, 06;02;00 finishes,
        Customer cust2 = new Customer(21630, 90, 5.00);     //06;00;30 arrival, waits 90 seconds, 06;03;30 finishes
        Customer cust3 = new Customer(21645, 30, 9.50);    //06;00;45 arrival, waits 2 min 45 seconds, 06;04;00 finishes
        
        test.addCashier(c);
        test.addCustomer(cust1);
        test.addCustomer(cust2);
        test.addCustomer(cust3);
        
        test.run();
        
        assertEquals(100.00, test.getAvgWaitTime(), 0.00);
        assertEquals(120.00, test.getMaxWaitTime(), 0.00);
    }
}