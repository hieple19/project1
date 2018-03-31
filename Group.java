
import java.util.*;

/**
 * Group class defines the functionality of Group objects, a base of the simulation.
 * It also defines functionality for its child classes.
 * 
 * @author Hiep Le
 */

public class Group{
    /**
     * The class holds a list of Person objects and a list of Strings.
     * The two lists are for members (Person objects) and memberTypes.
     * It also holds ride list of rides taken and rides that the group can take.
     * A wait time variable specifies the group's time in a ride's queue.
     */

    protected ArrayList<Person> members;
    protected ArrayList<String> memberTypes = new ArrayList<String>();

    private int waitTime = 0;

    protected RideList ridesTaken = new RideList();
    protected RideList ridesCanTake = new RideList();

    public Group(){
        members = new ArrayList<Person>();
    }

    /**
     * Getter methods to return group type, which is specifed by child classes.
     * @return Group's type
     */
    public String getGroupType(){
        return "Group not identified";}

    /**
     * Getter methods to return group's number of people.
     * @return Group's number of people
     */
    public int size() {                         
        return this.members.size();} 

    /**
     * Method resets group's wait time to 0.
     */
    public void resetWaitTime() {
        this.waitTime = 0;}

    /**
     * Method adds one to wait time.
     */
    public void updateWaitTime() {
        this.waitTime++;}

    public int getWaitTime() {
        return this.waitTime;}

    /**
     * Method looks through list to member to find member types.
     * Member types are then added to list of types.
     * Each type is added only once.
     */
    public void groupMemberTypes(){
        for(Person person: members){
            if(!memberTypes.contains(person.getType())){    // Check for duplication
                memberTypes.add(person.getType());
            }
        }
    }

    public String[] returnMemberTypes(){
        String[] result = new String[memberTypes.size()];
        for(int i = 0; i<result.length; i++){
            result[i] = memberTypes.get(i);
        }
        return result;
    }

    /**
     * Method looks through list to member to find minimum age of group.
     * @return Group's minimum age.
     */

    public int groupMinAge(){
        int minAge = 0;
        if(this.members.size() != 0){
            minAge = this.members.get(0).getAge();      // Set Min Age to age of first element
        }
        for(Person person: members){
            if(person.getAge() < minAge){
                minAge = person.getAge();               // Check through list for min age
            }
        }
        return minAge;
    }

    /**
     * Method checks ride's appeal with group.
     * Group member's type is checked with ride's appeal types
     * 
     * @param ride object of ride class
     * @return true if types match. 
     */

    public boolean checkRideAppeal(Ride ride){
        for(String memberType: this.memberTypes){
            if(ride.appealTypes.contains(memberType)){
                return true;
            }            
        }
        return false;
    }

    /**
     * Method finds rides in a ride list that a group can take.
     * Checks through list for a ride that appeal groups.
     * The ride must also have lower min age allowed than min age of group.
     * A ride is only added once.
     * 
     * @param rides list of rides
     */
    public void findRidesCanTake(RideList rides){
        for(Ride ride: rides.getRideList()){
            if(!this.ridesCanTake.contains(ride)){                 // Check for duplication
                if(ride.minAgeAllowed() <= this.groupMinAge()){    // Check ride's min age allowed < min age of group           
                    if(this.checkRideAppeal(ride)){                // Check ride's appeal
                        this.ridesCanTake.addRide(ride);
                    }
                }
            }
        }
    }

    /**
     * Method add a ride to group's list of rides taken
     * @param rides list of rides
     */

    public void addRidesTaken(Ride ride){
        if(!ridesTaken.contains(ride)){
            this.ridesTaken.addRide(ride);}
    }

    /**
     * Method checks if group has taken all rides it can take
     * @return true if ridesTaken and ridesCanTake list have the same size
     */
    public boolean takenAll(){
        if(ridesTaken.size() == ridesCanTake.size()){
            return true;
        }
        return false;
    }

    /**
     * Method finds a ride for group to join ride's queue.
     * 
     * @param rides list of rides
     * @return true if group is able to join one ride of list
     */
    public boolean findRide(RideList rides){
        for(Ride ride: rides.getRideList()){
            if(ridesCanTake.contains(ride)){            // Check ride can be taken by group
                if(!ridesTaken.contains(ride)){         // Check ride has not been taken
                    if(ride.checkAvailable(this)){      // Check ride has enough capacity in line
                        ride.joinLine(this);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void printMember(){
        for(Person p: members){
            System.out.println(p.getType() + " " + p.getAge());
        }
    }

}
