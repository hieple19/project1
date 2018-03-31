import java.util.*;

/**
 * RandomGaussian class is used to generate random numbers of a gaussian distribution
 * 
 * @author Hiep Le
 */
public class RandomGaussian{
    /** 
     * Test by printing results to console
     */
    public static void main(String[] args){
        RandomGaussian r1 = new RandomGaussian(20,2.5,400032);
        for(int i = 0 ; i<50; i++){
            System.out.println(r1.getGaussian());
        }
    }
    /**
     * Class holds a random object, mean and standard deviation of the distribution
     */
    private Random randomGaussian;
    protected double mean;
    protected double variance; 
    protected double stdDev;

    private long seed;

    /**
     * Constructor takes data for parameters from input file.
     * @param mean
     * @param stdDev
     * @param seed
     */
    public RandomGaussian(double mean, double stdDev, long seed){
        this.randomGaussian = new Random(seed);
        this.seed = seed;
        this.mean = mean;
        this.stdDev = stdDev;
        this.variance = stdDev*stdDev;
    }

    /**
     * Method generates a random integer based on the distribution
     */
    public int getGaussian(){
        double value = mean + randomGaussian.nextGaussian()*variance;
        int valueInt = (int) value;
        return valueInt;
    }

    public void print(){
        System.out.println(this.mean + " " + this.stdDev + " " + this.seed);
    }

    /**
     * Method compares 2 randomGaussian objects.
     * For testing purposes.
     */
    public boolean compareTo(RandomGaussian r){                                    
        return (this.mean == r.mean && this.stdDev == r.stdDev && this.seed == r.seed);
    }

}