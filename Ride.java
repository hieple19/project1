import java.util.*;

/**
 * Ride class defines functionaly for rides, basis of the simulation.
 * 
 * @author Hiep Le
 */

public class Ride implements Comparable{

    /**
     * Class holds info about ride's type, capacity, length,
     * space needed, max line size and appeal groups.
     * Also has info about total number of groups that joined queue and the max wait time.
     * Total wait time of all groups used to calculate average wait time.
     * 
     * Class also has info about the ride size, groups riding, groups riding and groups that finished.
     */

    protected String type;

    protected int capacity;
    protected int length;
    protected int space;
    protected int maxLine;
    protected ArrayList<String> appealTypes = new ArrayList<String>();
    protected int currentRide;

    protected int totalNumberOfGroups;
    protected int totalNumberOfPeople;
    protected int totalWaitTime;
    protected int maxWaitTime;

    protected int rideTime = 0; 

    protected boolean idle = true;
    protected boolean finished = false;

    protected GroupList line = new GroupList();
    protected GroupList groupsFinished = new GroupList();
    protected GroupList groupsRiding = new GroupList();

    /**
     * Constructor creates ride based on template from input file.
     * @param template template imported from input file.
     */
    public Ride(RideTemplate template){
        this.type = template.getType();
        this.capacity = template.getCapacity();
        this.length = template.getLength();
        this.space = template.getSpace();
        this.maxLine = template.getMaxLine();
        this.appealTypes = template.getGroups();

        this.currentRide = 0;
        this.totalNumberOfGroups = 0;
        this.totalNumberOfPeople = 0;
        this.totalWaitTime = 0;

    }

    /*public void print(){
    System.out.println("type " + this.type);
    System.out.println("capacity " + this.capacity);
    System.out.println("length " + this.length);
    System.out.println("this.space " + this.space);        
    System.out.println("maxLine " + this.maxLine);
    for(String s: this.appealTypes){
    System.out.print(s + " ");
    }
    System.out.println(" ");
    }*/

    public String toString(){
        return this.type;
    }

    /**
     * Getter method returns line's number of people.
     * @return number of people on line.
     */

    public int lineSize(){
        return this.line.peopleCurrentNumber();
    }

    /**
     * Method adds a group to line
     * @param Group 
     */
    public void joinLine(Group group){
        this.totalNumberOfGroups++;         // Increments number of groups/ people
        this.totalNumberOfPeople = this.totalNumberOfPeople + group.size();
        group.resetWaitTime();              // Reset group wait time to 0
        this.line.addGroup(group);
    }

    /**
     * Method check if ride's line has enough space for a group
     * @param Group
     */
    public boolean checkAvailable(Group group){
        return (this.maxLine - this.lineSize()) >= group.size();
    }

    /**
     * Method check for min age allowed based on ride's list of appeal types
     * @return minimum age allowed on a ride
     */
    public int minAgeAllowed(){

        if(appealTypes.contains("toddler")){
            return 0;
        }
        else if(appealTypes.contains("adolescent")){
            return 5;
        }
        else if(appealTypes.contains("teenager")){
            return 13;
        }
        else if(appealTypes.contains("young_adult")){
            return 18;
        }
        return 22;
    }

    /**
     * Method updates wait time of groups in ride
     */

    public void updateWaitTime(){
        for(int i = 0; i<this.line.groupsCurrentNumber(); i++){
            this.totalWaitTime++;               // Increase group's total wait time by line size
        }
        this.line.updateWaitTime();             // Increment wait time of every group in line
        this.updateMaxWaitTime();               // Update max wait time of ride
    }

    /**
     * Method updates max wait time of a ride
     */

    public void updateMaxWaitTime(){                            // Search and compare every group in queue's wait time
        if(this.maxWaitTime < this.line.getMaxWaitTime()){
            this.maxWaitTime = this.line.getMaxWaitTime();
        }
    }

    public int getMaxWaitTime(){
        return this.maxWaitTime;
    }

    public int getAverageWaitTime(){
        if(this.totalNumberOfGroups != 0){
            return this.totalWaitTime/this.totalNumberOfGroups;
        }
        return 0;
    }

    /**
     * Method add ride to list of rides taken of groups on ride.
     * Increments ride time.
     */
    public void run(){
        groupsRiding.addRideTaken(this);
        this.rideTime++;
    }

    /**
     * Method load groups to ride and update info accordingly
     */
    public void load(){
        this.idle = false;                              // Set ride's idle status to false
        this.currentRide = 0;                           // Reset current ride size and time
        this.rideTime = 0;                              

        for(Group group: line.getGroupList()){
            if(currentRide >= this.capacity){           // Break search when ride is full to capacity
                break;
            }
            if((group.size() + this.currentRide) <= this.capacity){
                currentRide = currentRide + group.size();       // Add first group in line that fits the empty space
                groupsRiding.addGroup(group);           
            }
        }
        for(Group group: groupsRiding.getGroupList()){                  
            this.line.removeGroup(group);                       // Remove groups riding from line 
        }
    }

    /**
     * Method unloadload groups and update info accordingly
     */
    public void unload(){                   
        groupsFinished = new GroupList(groupsRiding);           // Add groups finished and clear groups riding
        groupsRiding.clearGroupList();
        this.currentRide = 0;                                   // Reset ride info
        this.rideTime = 0;
        this.idle = true;
        this.finished = false;
    }

    /**
     * Method returns finished groups
     * @return GroupList 
     */
    public GroupList returnFinished(){
        return this.groupsFinished;
    }

    /**
     * Method checks if ride has finished. 
     */

    public void checkFinished(){
        if(this.rideTime == this.length){
            finished = true;
        }
    }

    /**
     * Method updates every time step for a ride.
     * Each action (load, run, unload) takes one time step.
     * Each run of ride will take (length+2) time steps.
     */

    public void updateTimeStep(){

        if(idle){                                       
            this.groupsFinished.clearGroupList();           // Clear group list whenever a ride has finished
            if(this.line.groupsCurrentNumber() != 0){
                this.load();                                // Load ride if there are groups in line
            }
        }

        else if(finished){
            this.unload();                                  // Unload ride
        }   
        else{
            this.run();                                     // Ride running and check if has finished
            this.checkFinished();
        }
    }

    public void printRideData(){
        System.out.println(this.type);
        System.out.println("Total Number People " + this.totalNumberOfPeople);
        System.out.println("Total Number Groups " + this.totalNumberOfGroups);
        System.out.println("Total Wait Time " + this.totalWaitTime);
        System.out.println("Average Wait Time " + this.getAverageWaitTime());
        System.out.println("Max Wait Time " + this.getMaxWaitTime());
        System.out.println("Groups On Queue " + this.line.groupsCurrentNumber());
        System.out.println("Groups Riding " + this.groupsRiding.groupsCurrentNumber());
        System.out.println();
    }

    /**
     * Method compares ride based on line size.
     * For changing of ride's position in a ride list.
     * To simulate visitors looking for rides with the shortest line.
     * @return -1 if line size is smaller than ride compared.
     * @return 1 if line size greater.
     * @return 0 if line size is the same.
     */
    public int compareTo(Object o){
        Ride ride = (Ride) o;
        if(ride.lineSize() > this.lineSize()){
            return -1;
        }
        else if(ride.lineSize() < this.lineSize()){
            return 1;
        }
        return 0;
    }

}
