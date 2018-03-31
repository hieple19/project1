
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class RideListTest.
 *
 * @author  Hiep Le
 */
public class RideListTest
{   
    /**
     * Method tests if ride is added to list properly.
     */
    @Test
    public void addRideTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");    
        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        RideList list = new RideList();
        list.addRide(ride);

        ArrayList<Ride> rides = new ArrayList<Ride>();
        rides.add(ride);                                    // Expected list contains line above

        assertEquals("Test", rides, list.getRideList());
    }

    /**
     * Method tests if contain check is done as intended.
     */
    @Test
    public void containRideTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");    
        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        RideList list = new RideList();
        assertEquals("Contain Test", false, list.contains(ride));       // false before line is added
        list.addRide(ride);

        assertEquals("Contain Test", true, list.contains(ride));        // true after line is added
    }

    /**
     * Method tests if size of list is returned accurately.
     */
    @Test
    public void sizeTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");    
        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        RideList list = new RideList();
        assertEquals("Size Test", 0, list.size());

        list.addRide(ride);
        assertEquals("Size Test", 1, list.size());
        Ride ride2 = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        list.addRide(ride2);
        assertEquals("Size Test", 2, list.size());
    }

    /**
     * Method tests if list is sorted properly.
     */
    @Test
    public void sortTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));
        Ride ride2 = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));
        RideList list = new RideList();
        list.addRide(ride);
        list.addRide(ride2);
        ride.joinLine(new Individual(23));          // Ride 1 has bigger line size than ride 2

        list.updateRideList();

        RideList expected = new RideList();
        expected.addRide(ride2);                    // Expect ride 1 and 2 to swap position
        expected.addRide(ride);

        assertEquals("Two rides swapped", expected.getRideList(), list.getRideList());

    }
}
