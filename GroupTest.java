
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * JUnit test case class for Group class.
 *
 * @author  Hiep Le
 */
public class GroupTest
{   
    /**
     * Method tests if size of group is returned correctly.
     */
    @Test
    public void testSize(){
        Group test = new Family(2);
        assertEquals("Group size", test.size(), 4);
    }

    /**
     * Method tests  if wait time is update correctly.
     */
    @Test
    public void testWaitTime(){
        Group test = new Family(2);
        assertEquals("Default Wait Time", test.getWaitTime(), 0);

        test.updateWaitTime();
        test.updateWaitTime();
        test.updateWaitTime();

        assertEquals("Updated Wait Time", test.getWaitTime(), 3);

        test.resetWaitTime();
        assertEquals("Reset wait time", test.getWaitTime(), 0);
    }

    /**
     * Method tests if member types is accurate.
     */
    @Test
    public void testMemberTypes(){
        Group test1 = new Family(2);

        String[] memberTypes1 = {"adult", "adolescent"};
        assertArrayEquals("Family default type", test1.returnMemberTypes(), memberTypes1);

        Group test2 = new Individual(19);
        String[] memberTypes2 = {"young_adult"};
        assertArrayEquals("Family default type", test2.returnMemberTypes(), memberTypes2);

        Group test3 = new Friends();
        String[] memberTypes3 = {"toddler", "adolescent", "teenager", "young_adult", "adult"};
    }

    /**
     * Method tests if group min's age is accurate.
     */
    @Test
    public void groupMinAge(){
        Group test1 = new Family(2);            // Default min age = 10

        assertEquals("Min age family", test1.groupMinAge(),10);

        Group test2 = new Individual(14);
        assertEquals("Min age individual", test2.groupMinAge(),14);

        Group test3 = new Friends();
        assertEquals("Min age individual", test3.groupMinAge(),1);
    }

    /**
     * Method tests if checkRideAppeal() gives expected results
     */
    @Test
    public void testCheckRideAppeal(){
        Group test = new Individual(15);
        Group test2 = new Family(2);

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        RideTemplate template = new RideTemplate("roller_coaster",80,3,20,120, appealGroups);

        // New Ride Constructed with appeal group of only adults

        Ride ride = new Ride(template);        
        assertEquals("Appeal false", false, test.checkRideAppeal(ride));
        assertEquals("Appeal true", true, test2.checkRideAppeal(ride));

        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        // New Ride Constructed with appeal group of all types

        template = new RideTemplate("roller_coaster",80,3,20,120, appealGroups);
        Ride ride2 = new Ride(template);

        assertEquals("Appeal true", true, test.checkRideAppeal(ride2));
        assertEquals("Appeal true", true, test2.checkRideAppeal(ride2));
    }

    /**
     * Method tests if ridesCantake gives expected results
     */
    @Test 
    public void testRideCanTake(){
        Group test1 = new Family(2);
        Group test2 = new Individual(24);
        Group test3 = new Friends();

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        // New Ride List with one ride that appeals only adults
        RideList list = new RideList();
        list.addRide(ride);

        RideList expected = new RideList();

        test1.findRidesCanTake(list);
        assertEquals("Ride Can Take 0 Rides", expected.getRideList(), test1.ridesCanTake.getRideList());

        test2.findRidesCanTake(list);       // Find Rides Can Take for group
        expected.addRide(ride);

        assertEquals("Ride Can Take 1 ride ", expected.getRideList(), test2.ridesCanTake.getRideList());
    }

    /**
     * Method tests if ridesCantake gives expected results
     */
    @Test
    public void testRideCanTake2(){
        Group test1 = new Family(2);
        Group test2 = new Individual(24);
        Group test3 = new Friends();

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");

        Ride ride2 = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        appealGroups.add("young_adult");
        appealGroups.add("teenager");
        appealGroups.add("adolescent");
        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        // New Ride List with two rides. One appeals only adults. One appeals all.
        RideList list = new RideList();
        list.addRide(ride);
        list.addRide(ride2);

        RideList expected = new RideList();

        test3.findRidesCanTake(list);
        assertEquals("Ride Can Take 0 Rides", expected.getRideList(), test1.ridesCanTake.getRideList());

        test1.findRidesCanTake(list);
        expected.addRide(ride);
        assertEquals("Ride Can Take 1", expected.getRideList(), test1.ridesCanTake.getRideList());

        test2.findRidesCanTake(list);
        expected.addRide(ride2);
        assertEquals("Ride Can Take 2", expected.getRideList(), test2.ridesCanTake.getRideList());
    }

    /**
     * Method tests if addRideTaken gives expected results
     */
    @Test
    public void testAddRideTaken(){
        Group test = new Individual(24);

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        test.addRidesTaken(ride);
        RideList expected = new RideList();
        expected.addRide(ride);
        assertEquals("Ride Taken", expected.getRideList(), test.ridesTaken.getRideList());
    }

    /**
     * Method tests if takenAll() gives expected results
     */
    @Test
    public void takenAllTest(){
        Group test1 = new Family(2);
        Group test2 = new Individual(24);

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        Ride ride2 = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        appealGroups.add("young_adult");
        appealGroups.add("teenager");
        appealGroups.add("adolescent");

        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        RideList list = new RideList();      //Ride List with two rides
        list.addRide(ride);
        list.addRide(ride2);

        test1.findRidesCanTake(list);       // test1 can take one ride
        test2.findRidesCanTake(list);       // test2 can take both rides
        assertEquals("Ride Taken", false, test1.takenAll());
        assertEquals("Ride Taken", false, test2.takenAll());

        test1.addRidesTaken(ride);
        test2.addRidesTaken(ride);
        assertEquals("Ride Taken", true, test1.takenAll());
        assertEquals("Ride Taken", false, test2.takenAll());

        test2.addRidesTaken(ride2);
        assertEquals("Ride Taken", true, test2.takenAll());
    }

    /**
     * Method tests if findRides() gives expected results
     */
    @Test
    public void findRidesTest(){
        Group test1 = new Family(2);

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");
        appealGroups.add("adolescent");
        Ride ride2 = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));
        RideList list = new RideList();

        assertEquals("No Ride ", false, test1.findRide(list));

        list.addRide(ride2);
        test1.findRidesCanTake(list);
        assertEquals("Can Join Line ", true, test1.findRide(list));     // test1 can only take ride 2

        // test1 took the ride and has no rides left that it can take
        test1.addRidesTaken(ride2);                                    
        assertEquals("Taken and No Line left ", false, test1.findRide(list));

        ride2 = new Ride(new RideTemplate("roller_coaster",80,3,20,3, appealGroups));
        test1 = new Family(2);
        RideList list2 = new RideList();
        assertEquals("Not enough space on line ", false, test1.findRide(list)); // Test for case with no space on line
    }
}
