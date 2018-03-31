import java.util.*;

/**
 * RideTemplate class holds information about the ride to be created and number of rides to create
 * 
 * @author Hiep Le
 */
public class RideTemplate{

    /**
     * Class holds info about the ride's type, appeal groups, space, length, capacity, maxLine.
     * Also holds info about number of objects per template.
     */

    private String type;
    private ArrayList<String> appealGroups;
    private int space;
    private int length;
    private int capacity;
    private int maxLine;

    private int numberOfObjects;

    /**
     * Constructor takes data for parameters from input file.
     * @param type
     * @param space
     * @param length
     * @param capacity
     * @param maxLine size
     * @param types list of appeal types
     */
    public RideTemplate(String type, int space, int length, int capacity, int maxLine, ArrayList<String> types){
        this.type = type;
        this.capacity = capacity;
        this.length = length;
        this.space = space;
        this.maxLine = maxLine;
        this.appealGroups = new ArrayList<String>();

        this.numberOfObjects = 0;
        for(String appealType: types){
            this.appealGroups.add(appealType);
        }
    }

    /**
     * Method set template's number of objects to create.
     */
    public void setNumberOfObjects(int number){this.numberOfObjects = number;}

    public int getNumberOfObjects(){return this.numberOfObjects;}

    public String getType(){return this.type;}

    public ArrayList<String> getGroups(){return this.appealGroups;}

    public int getSpace(){return this.space;}

    public int getLength(){return this.length;}

    public int getCapacity(){return this.capacity;}

    public int getMaxLine(){return this.maxLine;}


    public void printTemplate(){
        System.out.println("Type " + this.type);
        System.out.println("Group " + this.listToString());
        System.out.println("Space " + this.space);
        System.out.println("Length " + this.length);
        System.out.println("Capacity " + this.capacity);
        System.out.println("MaxLine " + this.maxLine);
        System.out.println("Number of rides " + this.numberOfObjects);
    }
    /**
     * Method compares two templates.
     * For testing purposes.
     * @return true if two templates are exactly the same.
     */
    public boolean compareTo(RideTemplate t){
        if(!this.type.equals(t.getType())){ return false;}

        if(this.space != t.getSpace()){ return false;}

        if(this.length != t.getLength()){return false;}

        if(this.capacity != t.getCapacity()){return false;}

        if(this.maxLine != t.getMaxLine()){return false;}

        if(!this.listToString().equals(t.listToString())){return false;}

        return true;
    }

    public String listToString(){
        String result = "";
        for(String s: appealGroups){
            result = result + s + ",";
        }
        return result;
    }
}