import java.util.*;

/**
 * GroupList class holds objects of group class.
 * It is used for calling the same method on all classes.
 * 
 * @author Hiep Le
 */

public class GroupList{

    /**
     * Class holds a list of groups,
     * records totoal number of groups and people
     * who have been part of the group (even if they have left the group)
     */

    protected ArrayList<Group> groups = new ArrayList<Group>();
    private int numberGroupsTotal;
    private int numberPeopleTotal;

    public GroupList(){
        this.numberGroupsTotal = 0;
        this.numberPeopleTotal = 0;
    }

    public GroupList(GroupList copy){
        for(Group group: copy.getGroupList()){
            this.addGroup(group);
        } 
    }

    /**
     * Method clears the list of all groups
     */
    public void clearGroupList(){
        this.groups.clear();
    }

    /**
     * Method checks if a group is part of a list
     * @param group
     * @returns true if group is present in list
     */
    public boolean contains(Group group){
        return this.groups.contains(group);
    }

    /**
     * Method adds a group to the list
     * @param group
     */
    public void addGroup(Group group){
        this.groups.add(group);
        numberGroupsTotal++;                                        // Update number of groups 
        numberPeopleTotal = numberPeopleTotal + group.size();       // Update number of people
    }

    /**
     * Method adds groups from another list to object's list
     * @param groupList list to copy groups from
     */
    public void addGroups(GroupList copy){
        for(Group group: copy.getGroupList()){
            this.addGroup(group);
            numberGroupsTotal++;
            numberPeopleTotal = numberPeopleTotal + group.size();
        } 
    }

    /**
     * Method removes a group from the list
     * @param group
     */
    public void removeGroup(Group group){
        this.groups.remove(group);
    }

    /**
     * Method removes groups from the list
     * @param groupList
     */
    public void removeGroups(GroupList groups){
        for(Group group: groups.getGroupList()){
            this.removeGroup(group);
        }
    }

    public ArrayList<Group> getGroupList(){
        return this.groups;
    }

    /**
     * Method returns current number of people in list.
     * Different from total number of people who have been part of the group but may 
     * have been removed.
     * 
     * @returns number of people currently in list.
     */
    public int peopleCurrentNumber(){
        int size = 0;
        for(Group group: groups){
            size = size + group.size();
        }
        return size;
    }

    /**
     * Method returns current number of groups in list.
     * Different from total number of groups who have been part of the group but may 
     * have been removed.
     * 
     * @returns number of groups currently in list.
     */
    public int groupsCurrentNumber(){
        return this.groups.size();
    }

    /**
     * Method adds a ride to the rides taken list of all groups in the list
     */

    public void addRideTaken(Ride ride){
        for(Group group: groups){
            group.addRidesTaken(ride);
        }
    }

    /**
     * Method calls on all groups of list to find a ride from a ride list
     * 
     * @param rideList list of rides
     * @return list of groups that successfully found a ride
     */

    public GroupList findRide(RideList rides){
        GroupList groupsWhichFoundRides = new GroupList();      // Result list to return
        for(Group group: groups){
            if(group.findRide(rides)){
                groupsWhichFoundRides.addGroup(group);
            }
        }
        return groupsWhichFoundRides;
    }

    /**
     * Method calls on all groups of list to update the rides they can take  from a ride list.
     * 
     * @param rideList list of rides
     */
    public void findRidesCanTake(RideList rides){
        for(Group group: this.groups){          
            group.findRidesCanTake(rides);
        }
    }

    /**
     * Method calls on all groups of list to update their wait time.
     * Used for list of groups on a ride's line.
     * 
     */

    public void updateWaitTime(){
        for(Group group: this.groups){
            group.updateWaitTime();
        }
    }

    /**
     * Method searches through list of groups for the max wait time.
     * Usually called on list of groups in a ride's line.
     * @return max wait time of groups in a line at present time.
     */

    public int getMaxWaitTime(){
        int max = 0;
        for(Group group: this.groups){
            if(group.getWaitTime() > max){
                max = group.getWaitTime();
            }
        }
        return max;
    }

    /**
     * Method searches through this list for groups that have taken all rides.
     * 
     * @return list of groups that took all rides it can take
     */
    public GroupList groupsTakenAll(){
        GroupList tmp = new GroupList();
        for(Group group: this.groups){
            if(group.takenAll()){               // Call takenAll() on every group
                tmp.addGroup(group);            // If true, group is added to result 
            }
        }
        return tmp;
    }

    /*public void printGroupList(){
        for(Group group: groups){
            group.printGroup();
        }
    }*/
}