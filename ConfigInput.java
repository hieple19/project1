import java.io.*;
import java.util.*;

/**
 * ConfigInput class is used to read configuration files.
 * 
 * @author Hiep Le
 */
public class ConfigInput{    

    /**
     * Class reads a file and uses a scanner to read the configuration.
     * Two lists are used to store the templates and the random gaussian generators.
     * It also reads percentage for groups of family, friends and individual.
     * Info about park size and seed is also read.
     */

    private FileReader file;                     
    private String dir;   
    private Scanner reader;

    private int parkSize;
    private double individualPercentage;
    private double friendPercentage;
    private double familyPercentage;

    private long seed;

    private ArrayList<RideTemplate> templates = new ArrayList<RideTemplate>();
    private ArrayList<RandomGaussian> randomGenerators = new ArrayList<RandomGaussian>();

    /**
     * Constructor takes a parameter input file
     * @param dir of file
     */
    public ConfigInput(String dir){
        try{
            this.dir = dir;    
            this.file = new FileReader(this.dir);
            this.reader = new Scanner(this.file);

        }catch(FileNotFoundException e) {
            System.out.println("No file found");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ArrayList<RideTemplate> returnTemplates(){return this.templates;}

    public ArrayList<RandomGaussian> returnRandomGenerators(){return this.randomGenerators;}

    public int getParkSize(){return this.parkSize;}

    public double getIndividualPercentage(){return this.individualPercentage;}

    public double getFriendPercentage(){return this.friendPercentage;}

    public double getFamilyPercentage(){return this.familyPercentage;}

    public long getSeed(){return this.seed;}

    /**
     * Method reads file
     */
    public void readFile(){
        try{
            String line = reader.nextLine();

            while(reader.hasNextLine()){
                if(line.equals("park")){
                    line = reader.nextLine();                           // Read info about park size
                    String[] parkLine = line.split(" ");
                    this.parkSize = Integer.parseInt(parkLine[1]);

                    line = reader.nextLine();
                    String[] seedLine = line.split(" ");                // Read info about seed
                    this.seed = Long.parseLong(seedLine[1]);
                }
                if(line.equals("ride")){
                    this.readRideInfo();                                // Read ride template info
                }
                if(line.equals("people")){
                    this.getGaussians();                                // Read random gaussian generators info 
                }

                line = reader.nextLine();
                if(line.equals("end file")){
                    break;                                              // End file
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        finally{
            if(reader != null){
                reader.close();
            }
        }
    }

    public void readRideInfo(){
        try{
            String line = this.reader.nextLine();

            String[]typeLine = line.split(" ");
            String type = typeLine[1];  

            line = reader.nextLine();
            String[]appealLine = line.split(" ");
            String[] appealGroups = appealLine[1].split(",");
            ArrayList<String> appealList = new ArrayList<String>();         // Read info about ride's appeal groups

            for(String group: appealGroups){
                appealList.add(group);
            }

            line = reader.nextLine();                                       // Read info about ride space
            String[]spaceLine = line.split(" ");
            int space = Integer.parseInt(spaceLine[1]);

            line = reader.nextLine();                                       // Read info about ride length
            String[]lengthLine = line.split(" ");
            int length = Integer.parseInt(lengthLine[1]);

            line = reader.nextLine();                                       // Read info about ride capacity
            String[]capacityLine = line.split(" ");
            int capacity = Integer.parseInt(capacityLine[1]);

            line = reader.nextLine();                                       // Read info about ride's max line
            String[]maxLineLine = line.split(" ");
            int maxLine = Integer.parseInt(maxLineLine[1]);

            RideTemplate newTemplate = new RideTemplate(type,space,length,capacity,maxLine,appealList);
            this.templates.add(newTemplate);                                // Add template read to list of templates

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Reading ride info error" + e);
        }
    }

    public void getGaussians(){
        try{
            String[]arrival = this.reader.nextLine().split(" ");
            double arrivalMean = Double.parseDouble(arrival[1]);
            double arrivalStd = Double.parseDouble(arrival[2]);
            randomGenerators.add(new RandomGaussian(arrivalMean,arrivalStd,this.seed));     // RandomGaussian for number of groups arriving 

            String[]groupDistribution = this.reader.nextLine().split(" ");
            this.individualPercentage = Double.parseDouble(groupDistribution[1]);
            this.friendPercentage = Double.parseDouble(groupDistribution[2]);               
            this.familyPercentage = Double.parseDouble(groupDistribution[3]);                   // Info about percentages of groups arriving

            String[]friendAge = this.reader.nextLine().split(" ");
            double friendAgeMean = Double.parseDouble(friendAge[1]);
            double friendAgeStd = Double.parseDouble(friendAge[2]);
            randomGenerators.add(new RandomGaussian(friendAgeMean,friendAgeStd, this.seed));    // RandomGaussian for age of friends arriving 

            String[]friendNumber = this.reader.nextLine().split(" ");
            double friendNumberMean = Double.parseDouble(friendNumber[1]);
            double friendNumberStd = Double.parseDouble(friendNumber[2]);
            randomGenerators.add(new RandomGaussian(friendNumberMean,friendNumberStd, this.seed));  // RandomGaussian for number of friends arriving 

            String[]childNumber = this.reader.nextLine().split(" ");
            double childNumberMean = Double.parseDouble(childNumber[1]);
            double childNumberStd = Double.parseDouble(childNumber[2]);
            randomGenerators.add(new RandomGaussian(childNumberMean,childNumberStd, this.seed));    // RandomGaussian for number of child arriving 

            String[]childAge = this.reader.nextLine().split(" ");
            double childAgeMean = Double.parseDouble(childAge[1]);
            double childAgeStd = Double.parseDouble(childAge[2]);
            randomGenerators.add(new RandomGaussian(childAgeMean,childAgeStd, this.seed));          // RandomGaussian for age of child arriving 

            String[]individualAge = this.reader.nextLine().split(" ");
            double individualAgeMean = Double.parseDouble(individualAge[1]);
            double individualAgeStd = Double.parseDouble(individualAge[2]);
            randomGenerators.add(new RandomGaussian(individualAgeMean,individualAgeStd, this.seed));    // RandomGaussian for age of individual arriving 

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Reading ride info error" + e);
        }
    }
}

