import java.util.*;
import java.io.*;

/**
 * ParameterInput class is used to read parameter files.
 * 
 * @author Hiep Le
 */
public class ParameterInput
{   
    /**
     * Class reads a file and uses a scanner to read the paramters.
     * A HashMap is used to store the corresponding number of objects to each ride template wanted.
     */
    private FileReader file;                      
    private String dir;   
    private Scanner reader;

    private HashMap<String,Integer> map = new HashMap<String,Integer>();      // Ride name is used as key. Number of objects is the value

    /**
     * Constructor takes a parameter input file
     * @param dir of file
     */
    public ParameterInput(String dir){
        try{
            this.dir = dir;    
            this.file = new FileReader(this.dir);
            this.reader = new Scanner(this.file);

        } catch(FileNotFoundException e) {
            System.out.println("No file found");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method returns number of objects per template wanted
     * @param rideType name of ride
     */
    public int getNumber(String rideType){
        if(this.map.containsKey(rideType)){
            int number = this.map.get(rideType);
            return number;
        }
        return 0;
    }

    /**
     * Method reads file
     */
    public void readFile(){
        try{
            String line = this.reader.nextLine();
            while(reader.hasNextLine()){
                if(line.equals("ride")){
                    line = this.reader.nextLine();
                    String[] typeLine = line.split(" ");
                    String type = typeLine[1];                                      // Info about ride type

                    String[] numberLine = this.reader.nextLine().split(" ");
                    Integer number = (Integer) Integer.parseInt(numberLine[1]);     // Info about number of rides
                    this.map.put(type,number);                                      // Put info into the HashMap
                }
                if(!reader.hasNextLine()){
                    break;
                }
                line = reader.nextLine();
            }

        }
        catch( Exception e){
            e.printStackTrace();
            System.out.println(e);
        }    
    }
}
