
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class ParkTest.
 *
 * @author  Hiep Le
 */
public class ParkTest
{   
    /**
     * Method tests if ride is added to park properly.
     */
    @Test
    public void addRideTest(){
        Park park = new Park(120);

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        RideTemplate template = new RideTemplate("roller_coaster",20,3,120,80, appealGroups);
        Ride ride = new Ride(template);

        park.addRide(ride);

        RideList expected = new RideList();
        expected.addRide(ride);

        assertEquals("Adding Ride", expected.getRideList(), park.rideList.getRideList()); // Check expected list with park's ride list
    }

    /**
     * Method tests if group is added to park properly
     */
    @Test
    public void addArrivalTest(){
        Park park = new Park(200);

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        RideTemplate template = new RideTemplate("roller_coaster",20,3,120,80, appealGroups);
        Ride ride = new Ride(template);

        park.addRide(ride);

        Group test = new Individual(23);
        park.addNewArrival(test);

        GroupList expected = new GroupList();
        expected.addGroup(test);

        assertEquals("Rides Can Take", park.rideList.getRideList(), test.ridesCanTake.getRideList());           // Check group's list of rides can take contains ride in park
        assertEquals("Groups In Park", expected.getGroupList(), park.totalGroupsInPark.getGroupList());         // Check park's list of all groups in park  
        assertEquals("Groups Not In Queue", expected.getGroupList(), park.groupsNotInQueue.getGroupList());     // Check park's list of all groups not in queue
    }

    /**
     * Method tests if searchForQueue works as expected.
     * All groups in park search for a line to join.
     */
    @Test
    public void searchForQueueTest(){
        Park park = new Park(200);
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",80,3,20,20, appealGroups);
        Ride ride = new Ride(template);                 // Line size: 20

        park.addRide(ride);

        Group group1 = new Family(13);                  // Group size: 15
        Group group2 = new Friends();                   // Group size: 5
        Group group3 = new Individual(14);              // Group size: 1
        park.addNewArrival(group1);
        park.addNewArrival(group2);
        park.addNewArrival(group3);

        park.searchForQueue();                          // Group 1 and 2 will find line to join. Group 3 has none

        GroupList lineExpected = new GroupList();
        lineExpected.addGroup(group1);
        lineExpected.addGroup(group2);                  // Expected list of groups who found rides: 1 and 2

        GroupList noLineExpected = new GroupList();
        noLineExpected.addGroup(group3);                // Expected list of groups who found no ride: 3

        assertEquals("Groups No Line", noLineExpected.getGroupList(), park.groupsNotInQueue.getGroupList());
        assertEquals("Ground On Line", ride.line.getGroupList(), lineExpected.getGroupList());

    }

    /**
     * Method tests if searchFinished works as expected.
     * All rides in park returns groups who just finished the rides.
     */
    @Test
    public void searchFinishedTest(){
        Park park = new Park(200);
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",80,3,20,20, appealGroups);
        Ride ride = new Ride(template);
        Ride ride2 = new Ride(template);

        park.addRide(ride);
        park.addRide(ride2);

        Group group1 = new Family(13);
        Group group2 = new Friends();
        Group group3 = new Individual(14);

        park.addNewArrival(group1);
        park.addNewArrival(group2);
        park.addNewArrival(group3);

        park.searchForQueue();                                  // Group 1 and 2 join ride1. Group 3 join ride2

        GroupList expected = new GroupList();
        park.searchForFinishedGroups();

        assertEquals("No Finished Group",expected.getGroupList(), park.groupsNotInQueue.getGroupList());  // Ride has not run so no groups finished expected

        ride.load();
        ride.run();
        ride.unload();

        ride2.load();
        ride2.run();
        ride2.unload();

        park.searchForFinishedGroups();                         // Ride loads, runs and unloads, returns groups who have finished. 
                                                                // Ride 1 returns group1 and 2. Ride 2 returns group 3.

        expected.addGroup(group1);
        expected.addGroup(group2);
        expected.addGroup(group3);

        assertEquals("3 Finished Groups",expected.getGroupList(), park.groupsNotInQueue.getGroupList());    // Check with expected list
    }

     /**
     * Method tests if leave and leaveEarly works as expected.
     * All groups who took all the rides they want or could not find any line leave park.
     */
    @Test
    public void leaveTest(){
        Park park = new Park(200);

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",80,3,20,20, appealGroups);
        Ride ride = new Ride(template);

        park.addRide(ride);

        Group group1 = new Family(13);
        Group group2 = new Friends();
        Group group3 = new Individual(14);

        park.addNewArrival(group1);
        park.addNewArrival(group2);
        park.addNewArrival(group3);

        park.searchForQueue();                      // Group1 and 2 found ride. Group 3 did not.
        park.leaveEarly();                          // Group 3 left early

        ride.load();
        ride.run();
        ride.unload();

        park.searchForFinishedGroups();
        park.leaveWhenTakenAll();                           // Group 1 and 2 leave after taking all rides

        GroupList expectedLeave = new GroupList();
        expectedLeave.addGroup(group3);
        expectedLeave.addGroup(group1);
        expectedLeave.addGroup(group2);                     // All three groups have left park

        GroupList expectedTakenAll = new GroupList();
        expectedTakenAll.addGroup(group1);
        expectedTakenAll.addGroup(group2);                  // Group 1 and 2 left after taking all rides

        GroupList expectedLeaveEarly = new GroupList();
        expectedLeaveEarly.addGroup(group3);                // Group 3 left early

        assertEquals("Three groups left", park.groupsLeft.getGroupList(),  expectedLeave.getGroupList());
        assertEquals("Two groups took all", park.takenAll.getGroupList(),  expectedTakenAll.getGroupList());
        assertEquals("One left early", park.groupsLeftEarly.getGroupList(), expectedLeaveEarly.getGroupList());
    }

}
