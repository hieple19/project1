
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class GroupListTest.
 *
 * @author  Hiep Le
 */
public class GroupListTest
{   
    /**
     * Method tests if group is added to list properly
     */
    @Test
    public void addTest(){
        GroupList testList = new GroupList();
        Group family = new Family(2);

        testList.addGroup(family);
        ArrayList<Group> expected = new ArrayList<Group>();
        expected.add(family);

        assertEquals("Adding Test", expected, testList.getGroupList());     // Check expected list with test list
    }

    /**
     * Method tests if groups are added properly to list.
     */
    @Test
    public void addGroupsTest(){
        GroupList testList = new GroupList();
        Group family = new Family(2);
        Group family2 = new Family(1);
        testList.addGroup(family);                              // Test list is expected to hold 2 groups
        testList.addGroup(family2);

        GroupList test = new GroupList(testList);
        ArrayList<Group> expected = new ArrayList<Group>();             // Expected group consists of 2 groups
        expected.add(family);
        expected.add(family2);

        assertEquals("Add Groups", expected, test.getGroupList());
    }

    /**
     * Method tests if group is removed properly from list.
     */
    @Test
    public void removeGroupTest(){
        GroupList testList = new GroupList();
        Group family = new Family(2);

        testList.addGroup(family);
        ArrayList<Group> expected = new ArrayList<Group>();
        expected.add(family);

        assertEquals("Adding Test", expected, testList.getGroupList());

        testList.removeGroup(family);
        expected.remove(family);
        assertEquals("Removing Test", expected, testList.getGroupList());
    }

    /**
     * Method tests if groups are removed properly from list.
     */
    @Test
    public void removeGroupsTest(){
        GroupList testList = new GroupList();
        GroupList testList2 = new GroupList();
        Group family = new Family(2);
        Group family2 = new Family(1);
        Group family3 = new Family(3);

        testList.addGroup(family);
        testList.addGroup(family2);

        testList2.addGroup(family);
        testList2.addGroup(family2);
        testList2.addGroup(family3);

        testList2.removeGroups(testList);                           // List 2 has family1 and 2 removed, has family 3 left
        ArrayList<Group> expected = new ArrayList<Group>();
        expected.add(family3);                                      // Expected list holds family 3

        assertEquals("Remove Groups", expected, testList2.getGroupList());

    }

    /**
     * Method tests if contains method works.
     */
    @Test 
    public void containTest(){
        GroupList testList = new GroupList();
        Group family = new Family(2);
        Group family2 = new Family(1);

        testList.addGroup(family);

        assertEquals("Contain Test", true, testList.contains(family));
        assertEquals("Contain Test False", false, testList.contains(family2));
    }

    /**
     * Method tests if people current number are returned as expected. 
     */
    @Test
    public void peopleCurrentNumberTest(){
        GroupList testList = new GroupList();

        Group family = new Family(2);                   // Size: 4
        Group family2 = new Family(1);                  // Size: 3

        testList.addGroup(family);
        testList.addGroup(family2);

        assertEquals("People Current Number", 7, testList.peopleCurrentNumber());
    }

    /**
     * Method tests if groups current number are returned as expected. 
     */
    @Test
    public void groupsCurrentNumberTest(){
        GroupList testList = new GroupList();
        assertEquals("People Current Number", 0, testList.groupsCurrentNumber());
        Group family = new Family(2);
        Group family2 = new Family(1);

        testList.addGroup(family);              // Expect 2 groups in list
        testList.addGroup(family2);

        assertEquals("People Current Number", 2, testList.groupsCurrentNumber());
    }

    /**
     * Method tests if ride taken is added as expected
     */
    @Test
    public void addRideTakenTest(){
        Group test1 = new Family(2);
        Group test2 = new Individual(24);
        GroupList list = new GroupList();
        list.addGroup(test1);
        list.addGroup(test2);

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");

        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));

        list.addRideTaken(ride);                    // Add ride to list of ride taken for group 1 and 2

        RideList expected = new RideList();
        expected.addRide(ride);

        assertEquals("Ride Taken", expected.getRideList(), test1.ridesTaken.getRideList());     // Check for group 1
        assertEquals("Ride Taken", expected.getRideList(), test2.ridesTaken.getRideList());     // Check for group 2
    }

    /**
     * Method tests if findRide works as expected.
     */
    @Test
    public void findRideTest(){
        Group test1 = new Family(2);
        Group test2 = new Individual(1);
        Group test3 = new Individual(13);
        GroupList groups = new GroupList();
        groups.addGroup(test1);
        groups.addGroup(test2);
        groups.addGroup(test3);

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");
        appealGroups.add("adolescent");
        Ride ride2 = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));      // Max line is 5 people     
        RideList list = new RideList();

        list.addRide(ride2);
        test1.findRidesCanTake(list);
        test3.findRidesCanTake(list);
        test2.findRidesCanTake(list);               //Group 1 and 3 can join the line, but group 2 cannot as no space on line

        GroupList result = groups.findRide(list);

        GroupList expected = new GroupList();
        expected.addGroup(test1);
        expected.addGroup(test3);                   // Expected 2 groups to find ride

        assertEquals("Find Ride", expected.getGroupList(), result.getGroupList());

    }

    /**
     * Method checks if wait time is updated for every group 
     */
    @Test
    public void updateWaitTime(){
        Group test1 = new Family(2);
        Group test2 = new Individual(1);
        Group test3 = new Individual(13);

        GroupList list = new GroupList();
        list.updateWaitTime();
        assertEquals("Update Empty List", 0, test1.getWaitTime());
        assertEquals("Update Empty List", 0, test2.getWaitTime());
        assertEquals("Update Empty List", 0, test3.getWaitTime());

        list.addGroup(test1);
        list.updateWaitTime();
        assertEquals("Update List with only test1", 1, test1.getWaitTime());
        assertEquals("Update List with only test1", 0, test2.getWaitTime());
        assertEquals("Update List with only test1", 0, test3.getWaitTime());

        list.addGroup(test2);
        list.updateWaitTime();
        assertEquals("Update List with test1 and test2", 2, test1.getWaitTime());
        assertEquals("Update List with test1 and test2", 1, test2.getWaitTime());
        assertEquals("Update List with test1 and test2", 0, test3.getWaitTime());

        list.addGroup(test3);
        list.updateWaitTime();
        assertEquals("Update List With All three tests", 3, test1.getWaitTime());
        assertEquals("Update List With All three tests", 2, test2.getWaitTime());
        assertEquals("Update List With All three tests", 1, test3.getWaitTime());

    }

    /**
     * Method checks if max time is updated accurately 
     */
    @Test
    public void testMaxWaitTime(){
        Group test1 = new Family(2);
        Group test2 = new Individual(1);
        Group test3 = new Individual(13);

        GroupList list = new GroupList();
        list.updateWaitTime();
        assertEquals("Max Empty", 0, list.getMaxWaitTime());

        list.addGroup(test1);
        list.updateWaitTime();
        assertEquals("Max With One Group", 1, list.getMaxWaitTime());    // Max wait time is for group1 - 1

        list.addGroup(test2);
        list.updateWaitTime();
        assertEquals("Max With 2 Groups", 2, list.getMaxWaitTime());    // Max wait time is for group1 - 2

        list.addGroup(test3);
        list.updateWaitTime();
        assertEquals("Max With 3 Groups", 3, list.getMaxWaitTime());    // Max wait time is for group1 - 3
    }

    /**
     * Method checks if takenAll() updates correctly
     */
    @Test
    public void testTakenAll(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",80,3,20,120, appealGroups);
        Ride ride = new Ride(template);

        RideList rides = new RideList();
        rides.addRide(ride);

        Group group1 = new Family(2);
        Group group2 = new Friends();
        Group group3 = new Individual(23);

        ride.joinLine(group1);
        ride.joinLine(group2);                  // Group 3 did not join ride

        ride.load();
        ride.run();
        ride.unload();                          // Update Rides Taken for group 1 and 2

        GroupList list = new GroupList();
        list.addGroup(group1);
        list.addGroup(group2);
        list.addGroup(group3);
        list.findRidesCanTake(rides);           // Group 1 and 2 has taken all the rides it can. Group 3 has not.

        GroupList expected = ride.returnFinished();  // Return group 1 and 2
        GroupList result = list.groupsTakenAll();   //  Return groups that have taken all: 1 and 2

        assertEquals("Taken all rides available", expected.getGroupList(), list.groupsTakenAll().getGroupList());
    }
}
