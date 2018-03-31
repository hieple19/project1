
import java.util.*;

/**
 * Park class holds objects of GroupList and RideList.
 * It handles the operations during every step of the park, adding and removing groups, 
 * movement of groups between queues.
 * 
 * @author Hiep Le
 */

public class Park{

    /**
     * Class holds information about park size, total number of people who visited and people who left
     * It also holds a ride list of rides in the park.
     * Several groupLists are used to keep track of:
     *      Groups currently in park
     *      Groups not in any ride's queue
     *      Groups who have left
     *      Groups who have left early
     *      Groups who have taken all rides
     */
    
    protected int size; 

    protected int peopleLeft;
    protected int peopleVisited;

    protected RideList rideList;

    protected GroupList totalGroupsInPark;
    protected GroupList groupsNotInQueue; 
    protected GroupList groupsLeft;
    protected GroupList groupsLeftEarly;
    protected GroupList takenAll;

    /**
     * Constructor creates park based on size, imported from input file.
     * @param size from input file.
     */
    public Park(int size){
        this.size = size;
        this.totalGroupsInPark = new GroupList();
        this.rideList = new RideList();
        this.groupsNotInQueue = new GroupList();
        this.groupsLeft = new GroupList();
        this.groupsLeftEarly = new GroupList();
        this.takenAll = new GroupList();
        this.peopleLeft = 0;
        this.peopleVisited = 0;
    }

    /**
     * Method adds a ride to park's ridelist
     * @param ride
     */
    public void addRide(Ride ride){
        this.rideList.addRide(ride);
    }

    /**
     * Method adds a new group to park's list of all groups
     * @param group
     */
    public void addNewArrival(Group newGroup){
        newGroup.findRidesCanTake(this.rideList);       // New group finds all rides it can take in park
        this.totalGroupsInPark.addGroup(newGroup);      // Add new group to list of all groups in park
        this.groupsNotInQueue.addGroup(newGroup);       // Add new group to list of groups not in any queue
        peopleVisited+=newGroup.size();                 // Update people visited
    }

    /**
     * Method searches ride list for groups that have finished the ride
     *      and add them back to the list of groups not in queue
     */
    public void searchForFinishedGroups(){              
        for(Ride ride: rideList.getRideList()){
            GroupList tmp = ride.returnFinished();      // Get list of finished groups from ride
            groupsNotInQueue.addGroups(tmp);            // Add back to groups not in queue list
        }
    }

    /**
     * Method find rides for all groups not in any queue. 
     * Any groups which found a ride is remvoed from list of groups not in any queue
     */
    public void searchForQueue(){
        GroupList groupsFoundRide = groupsNotInQueue.findRide(rideList);      // List of groups which found queue
        groupsNotInQueue.removeGroups(groupsFoundRide);                       // Remove groups from list of groups not in queue
    }

    /**
     * Method searches list of groups not in any queues for groups that have taken all rides they can 
     * These groups are then removed from list of groups in the park
     *      and added to list of groups who left and list of groups who took all rides
     */
    public void leaveWhenTakenAll(){
        GroupList groupsToLeave = groupsNotInQueue.groupsTakenAll();       // List of groups who took all rides
        groupsLeft.addGroups(groupsToLeave);                            // Add groups to list of groups who left/take all
        takenAll.addGroups(groupsToLeave);
        groupsNotInQueue.removeGroups(groupsToLeave);                   // Remove groups from relevant groups
        totalGroupsInPark.removeGroups(groupsToLeave);  
    }

    /**
     * Method searches list of groups who remain not park of any queues after searching for rides.
     *      These groups will leave the park early as they cannot find any rides.
     */
    public void leaveEarly(){
        groupsLeftEarly.addGroups(this.groupsNotInQueue);           // Add groups to list of groups that left early
        groupsLeft.addGroups(this.groupsNotInQueue);
        totalGroupsInPark.removeGroups(this.groupsNotInQueue);      // Remove groups from total groups in park
        this.groupsNotInQueue.clearGroupList();                     // Clear list of groups not in queue to prepare for next time step
    }

    /**
     * Method updates every time step for the park.
     */
    public void updateTimeStep(){
        this.searchForFinishedGroups();        // Groups which finished added to groupsNotOnQueue
        
        this.leaveWhenTakenAll();              // Remove any groups that took all from groupsNotOnQueue
        
        this.rideList.updateWaitTime();        // Update wait time for existing groups on ride's queue
        
        this.searchForQueue();                 // Search ride for groupsNotOnQueue
        
        this.rideList.updateRideList();        // Sort rides based on queue's size
        
        this.leaveEarly();                     // Remove groups without any queue
        
        this.rideList.updateTimeStep();        // Update all rides
    }


    public void printParkRides(){
        this.rideList.printRideList();
    }

    public void printStats(){        
        System.out.println("Total People Visited " + peopleVisited);
        System.out.println("Total People Currently in Park " + this.totalGroupsInPark.peopleCurrentNumber());
        System.out.println("Total People Left " + this.groupsLeft.peopleCurrentNumber());
        System.out.println("Total People Left Early " + this.groupsLeftEarly.peopleCurrentNumber());
        System.out.println("Total People Taken All " + this.takenAll.peopleCurrentNumber());
    }

}
