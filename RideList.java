import java.util.*;

/**
 * RideLst class holds objects of rides, used for calling the same methods on all rides.
 * 
 * @author Hiep Le
 */

public class RideList{

    /**
     * Class holds a list of rides
     */
    protected ArrayList<Ride> rides;

    public RideList(){
        this.rides = new ArrayList<Ride>();
    }

    /**
     * Method adds a ride to list of rides
     * @param ride 
     */
    public void addRide(Ride ride){
        this.rides.add(ride);
    }

    /**
     * Method checks if rideList contains a ride
     * @param ride
     * @return true if ride is present
     */
    public boolean contains(Ride ride){
        return this.rides.contains(ride);
    }

    /**
     * Method returns size of size of rideList
     * @return size of list
     */
    public int size(){
        return this.rides.size();
    }      

    /**
     * Method calls updateTimeStep on every ride in ride list
     */
    public void updateTimeStep(){
        for(Ride ride: rides){
            ride.updateTimeStep();
        }
    }

    /**
     * Method calls updateWaitTime on every ride in ride list
     */
    public void updateWaitTime(){
        for(Ride ride: rides){
            ride.updateWaitTime();
        }
    }

    /**
     * Method sorts list based on size of line.
     * This is to simulate visitors looking for line of smallest line.
     */
    public void updateRideList(){
        Collections.sort(this.rides);
    }

    public ArrayList<Ride> getRideList(){
        return this.rides;
    }

    public void printRideList(){
        for(Ride ride: rides){
            ride.printRideData();
        }
    }
}
