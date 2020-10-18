import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * SimTester is the main class that gathers the data from the given input.txt and 
 * runs the simulation of the CoffeeShop with the provided data.
 * 
 * @author ShakhSaidov
 */
public class SimTester {
    private static double profitMin;
    private static double profitMax;

    private static int serveMin;
    private static int serveMax;

    private static double cashierWage;

    private static ArrayList<Integer> arrivalTimes = new ArrayList<Integer>();          //List of Customers' times 
    private static ArrayList<Integer> lateArrivals = new ArrayList<Integer>();          //Late Customers who won't be served

    public static void main(String[] args) {
        int r = 19283756;       //random seed number
        int c = 6;              //number of cashiers
        Sim sim = setDataAndSimulate(r, c);
        System.out.println("Hiring " + c + " cashiers result: ");
        analyzeSim(sim);
    }

    /**
     * Reads input.txt and gathers the data
     * @param int seed 
     * @param int cashierNum
     * 
     * @return Sim sim with results
     */
    public static Sim setDataAndSimulate(int seed, int cashierNum){
        File input = new File("C:\\Users\\Shakh\\bluej\\Project 2\\Input.txt");         //read input.txt
        try {
            Scanner data = new Scanner(input);

            String[] profits = data.nextLine().split(" ");
            profitMin = new Double(profits[0]);                                 //low bound of profit
            profitMax = new Double(profits[1]);                                 //high bound of profit

            cashierWage = new Double(data.nextLine());

            String[] serveTime = data.nextLine().split(" ");
            serveMin = (int) new Integer(serveTime[0]);                         //low bound of serve time
            serveMax = (int) new Integer(serveTime[1]);                         //high bound of serve time
            while(data.hasNext()){
                String[] arrival = data.nextLine().split(":");
                //time is kept in seconds
                Integer arrivalTime = new Integer(arrival[0]) * 3600 + new Integer(arrival[1]) * 60 + new Integer(arrival[2]);
                if(arrivalTime >= 75600){            //if they come at or after 21:00, they aren't served
                    lateArrivals.add(arrivalTime);
                } else{
                    arrivalTimes.add(arrivalTime);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.out.println(e);
        }

        //run the simulation with the obtained data
        return simulate(seed, arrivalTimes, serveMin, serveMax, profitMin, profitMax, cashierNum, cashierWage);
    }

    /**
     * Uses the obtained data properly and sets the scene.
     * @param int seed, for random
     * @param ArrayList times, the arrival times of customers
     * @param int serveMin, lower bound for service time
     * @param int serveMax, upper bound for service time
     * @param double profitMin, lower bound for profit
     * @param double profitMax, upper bound for profit
     * @param int cashierNum, number of cashiers
     * @param double cashierWage, the wage cashiers receive
     * 
     * @return Sim sim containing results
     */
    public static Sim simulate(int seed, ArrayList<Integer> times, int serveMin, int serveMax, 
    double profitMin, double profitMax, int cashierNum, double cashierWage) {
        Sim sim = new Sim();
        for(int i = 0; i < cashierNum; i++){
            sim.addCashier(new Cashier());          //add the number of cashiers
        }
        sim.setCashierWage(cashierWage);            //set their wage

        Random random = new Random(seed);           //randomizing numbers (up to 2 decimal points for double #'s)
        for(int i = 0; i < times.size(); i++){
            int serveT = random.nextInt(serveMax - serveMin + 1) + serveMin;            
            double profit = Math.floor((random.nextDouble() * (profitMax - profitMin) + profitMin) * 100) / 100;
            //add customer with the arrival time, service time and profit
            sim.addCustomer(new Customer((int)times.get(i), serveT, profit)); 
        }

        sim.run();          //runs the simulation
        return sim;         //returns it to analyze it afterwards
    }

    /**
     * Analyzes the result of the simulation.
     * AvgWaitTime, MaxWaitTime, Overflow-to-Serviced ratio, NetProfit are all shown
     * @param Sim sim
     */
    public static void analyzeSim(Sim sim) {
        System.out.println("Number of Customers served: " + sim.getServedCSize());
        System.out.println("Number of Customers that came after 21:00 : " + lateArrivals.size());
        System.out.println();
        System.out.println("Number of Customers waited: " + sim.getWaitedCSize());
        System.out.println("Average customer waiting time : " + sim.getAvgWaitTime() + " seconds");
        System.out.println("Maximum customer waiting time : " + sim.getMaxWaitTime() + " seconds");
        System.out.println();
        System.out.println("Number of Customers overflown: " + sim.getOverflownCSize());
        System.out.println("Overflown-to-Served ratio: " + sim.getOverflownCSize() + "/" + sim.getServedCSize());
        System.out.println();
        System.out.println("Total money earned: " + sim.getTotalProfit() + " dollars");
        System.out.println("Total payment to cashiers: " + sim.getCashierPay() + " dollars");
        System.out.println("Net Profit of the Coffee Shop for the day: " + sim.getNetProfit() + " dollars");
    }
}