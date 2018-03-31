
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class RideTest.
 *
 * @author  Hiep Le
 * @version (a version number or a date)
 */
public class RideTest
{

    /**
     * Method tests if group size is returned correctly.
     */
    @Test
    public void lineSizeTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",20,3,120,80, appealGroups);
        Ride ride = new Ride(template);
        ride.joinLine(new Family(2));                       // Family size 4 with 2 children
        assertEquals("Line Size", ride.lineSize(), 4);

        ride.joinLine(new Individual(14));
        assertEquals("Line Size", ride.lineSize(), 5);

        ride.joinLine(new Friends());                       // Friends default size 5
        assertEquals("Line Size", ride.lineSize(), 10);
    }

    @Test
    /**
     * Method tests if checkAvailable returns as expected.
     */
    public void lineAvalaibleTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",80,3,20,5, appealGroups);
        Ride ride = new Ride(template);
        System.out.println(template.getMaxLine());
        assertEquals("Line Size", true, ride.checkAvailable(new Family(2)));        // Ride has line of capacity 5
        assertEquals("Line Size 2",  true, ride.checkAvailable(new Family(3)));
        assertEquals("Line Size fail", false, ride.checkAvailable(new Family(6)));
    }

    @Test
    /**
     * Method tests if minAgeTest returns as expected.
     */
    public void minAgeTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        Ride ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));
        assertEquals("Adult Min Age", 22, ride.minAgeAllowed());

        appealGroups.add("young_adult");
        ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));
        assertEquals("Young Adult Min Age", 18, ride.minAgeAllowed());

        appealGroups.add("teenager"); 
        ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));
        assertEquals("Teenager Min Age", 13, ride.minAgeAllowed());

        appealGroups.add("adolescent");
        ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));
        assertEquals("Adolescent Min Age", 5, ride.minAgeAllowed());

        appealGroups.add("toddler");
        ride = new Ride(new RideTemplate("roller_coaster",80,3,20,5, appealGroups));
        assertEquals("Toddler Min Age", 0, ride.minAgeAllowed());
    }

    @Test
    /**
     * Method tests if updateWaitTime works as expected to increment wait time every group on queue.
     */
    public void updateWaitTimeTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",20,3,120,80, appealGroups);
        Ride ride = new Ride(template);

        ride.joinLine(new Family(2));
        ride.updateWaitTime();                                      // Update wait time for 1 group from 0 to 1
        assertEquals("Ride Wait Time", 1, ride.totalWaitTime);
        assertEquals("Ride Max Wait Time", 1, ride.maxWaitTime);

        ride.joinLine(new Individual(14));
        ride.updateWaitTime();                                      // Update from 0 to 1 for one group, 1 to 2 for one group
        assertEquals("Ride Wait Time", 3, ride.totalWaitTime);
        assertEquals("Ride Max Wait Time", 2, ride.maxWaitTime);

        ride.joinLine(new Friends());                               // Update 0 to 1 for one, 1 to 2 for one, 2 to 3 for one
        ride.updateWaitTime();
        assertEquals("Ride Wait Time", 6, ride.totalWaitTime);
        assertEquals("Ride Max Wait Time", 3, ride.maxWaitTime);
    }

    /**
     * Method tests if ride loads as expected, allowing only certain number of groups on ride
     */
    @Test
    public void rideLoadingTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",80,3,20,120, appealGroups);
        Ride ride = new Ride(template);

        Group group1 = new Family(13);        // Group size 15
        Group group2 = new Friends();         // Group size 5
        Group group3 = new Individual(14);    // Group size 1
        ride.joinLine(group1);
        ride.joinLine(group2);
        ride.joinLine(group3);  

        ride.load();                           // Only two groups can be loaded as capacity is only 20

        ArrayList<Group> ridingExpected = new ArrayList<Group>();
        ridingExpected.add(group1);
        ridingExpected.add(group2);           // Expect 2 groups on ride

        ArrayList<Group> lineExpected = new ArrayList<Group>();
        lineExpected.add(group3);             // Expect one group left on line

        assertEquals("People Riding", ridingExpected, ride.groupsRiding.getGroupList());
        assertEquals("People OnLine", lineExpected, ride.line.getGroupList());

    }

    /**
     * Method tests if ride runs as expected, adding itself to list of rides taken for 
     * all groups on ride.
     */
    @Test
    public void rideRunningTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",80,3,20,120, appealGroups);
        Ride ride = new Ride(template);

        Group group1 = new Family(13);
        Group group2 = new Friends();
        Group group3 = new Individual(14);
        ride.joinLine(group1);
        ride.joinLine(group2);
        ride.joinLine(group3);  

        ride.load();

        assertEquals("Update ride time", 0, ride.rideTime);
        ride.run();
        assertEquals("Update ride time", 1, ride.rideTime);     // Test for ride time incrementing

        ride.run();
        ride.run();
        assertEquals("Update ride time", 3, ride.rideTime);     // Test for ride time incrementing

        RideList expected = new RideList();
        assertEquals("Group 3 Not Taken", group3.ridesTaken.getRideList(), expected.getRideList());
        expected.addRide(ride);                                 // Empty list for group 3 rides taken - group 3 still on line

        assertEquals("Group 1 Taken", group1.ridesTaken.getRideList(), expected.getRideList());
        assertEquals("Group 2 Taken", group2.ridesTaken.getRideList(), expected.getRideList());     // Taken list with above ride for group 1 and 2
    } 

    /**
     * Method tests if ride unloads as expected, removing groups from groupsRiding list
     * and adding such groups to groupsFinished list
     */
    @Test
    public void rideUnloadingTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");        
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template = new RideTemplate("roller_coaster",80,3,20,120, appealGroups);
        Ride ride = new Ride(template);

        Group group1 = new Family(13);
        Group group2 = new Friends();
        Group group3 = new Individual(14);
        ride.joinLine(group1);
        ride.joinLine(group2);
        ride.joinLine(group3);  

        ride.load();
        ride.run();
        ride.unload();

        GroupList expectedRiding = new GroupList();             // Expected empty list for groups riding
        GroupList expectedFinished = new GroupList();
        expectedFinished.addGroup(group1);                      // Expect lists of 2 groups for groups finished
        expectedFinished.addGroup(group2);

        assertEquals("Finished Groups", ride.groupsFinished.getGroupList(), expectedFinished.getGroupList());
        assertEquals("Riding Groups", ride.groupsRiding.getGroupList(), expectedRiding.getGroupList());
    }
}