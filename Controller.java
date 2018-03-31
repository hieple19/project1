import java.util.*;

/**
 * Controller class contains the main method. Handles the passing of time and set up of the park
 * 
 * @author Hiep Le
 */

public class Controller{

    /**
     * Controller holds the park object, timeStep variable that handles time.
     * It also contains percentage for groups of family, friends and individual.
     * It has two lists of random gaussian generators and list of ride templates.
     * All key information are imported from the input files. 
     * 
     * A randomGroupType random object helps to determine which group type to generate every minute
     */

    protected Park park;
    private static int timeStep = 0;

    private long seed = 10000;
    private double individualPercentage;
    private double friendPercentage;
    private double familyPercentage;

    private Random randomGroupType;

    private static ArrayList<RandomGaussian> randomGaussians = new ArrayList<RandomGaussian>() ;
    private static ArrayList<RideTemplate> templates = new ArrayList<RideTemplate>();

    /**
     * Constructor creates a Controller object that contains a park
     * @param park
     */
    public Controller(Park park){
        this.park = park;
    }

    /**
     * Method sets the seed value the randomGroupType generator.
     * @param input 
     */
    public void setSeed(ConfigInput input){
        this.seed = input.getSeed();
        this.randomGroupType = new Random(this.seed);
    }

    /**
     * Method calls park to simulate one time step.
     */
    public void parkSimulate(){
        this.park.updateTimeStep();
    }

    /**
     * Method sets percentage imported from input.
     */
    public void setPercentage(ConfigInput input){
        this.individualPercentage = input.getIndividualPercentage();
        this.friendPercentage = input.getFriendPercentage();
        this.familyPercentage = input.getFamilyPercentage();
    }

    /**
     * Method to simulate people arriving to the park
     */

    public void peopleArriving(){
        RandomGaussian randomGroupNumber = this.randomGaussians.get(0);                 // RandomGenerator for number of groups per minute
        int numberOfGroups = randomGroupNumber.getGaussian();

        while(numberOfGroups<0){
            numberOfGroups = randomGroupNumber.getGaussian();                           // Makes sure no negative number of groups per minute
        }

        for(int i = 0; i<numberOfGroups; i++){

            // RandomGenerator for group type of every group. Returns a double within 0 and 1
            double groupType = this.randomGroupType.nextDouble();                      

            // Which block runs depends on the groupType value that's returned

            if(groupType < this.individualPercentage){  
                Group newIndividual = new Individual(this.randomGaussians.get(5));      // New Individual is added to park. Age is randomly generated
                this.park.addNewArrival(newIndividual);
                newIndividual.groupMemberTypes();
                newIndividual.groupMinAge();

            }
            else if(groupType>=this.individualPercentage && groupType<(this.individualPercentage + this.friendPercentage)){
                Group newFriends = new Friends(this.randomGaussians.get(2),this.randomGaussians.get(1));        
                this.park.addNewArrival(newFriends);                                    // New Friends group is added. Age and Number of friends are randomly generated                        
                newFriends.groupMemberTypes();
                newFriends.groupMinAge();

            }
            else{
                Group newFamily = new Family(this.randomGaussians.get(3), this.randomGaussians.get(4));
                this.park.addNewArrival(newFamily);                                     // New family is added to park. Age and Number of children are randomly generated.
                newFamily.groupMemberTypes();
                newFamily.groupMinAge();
            }
        }
    }

    public static void main(String[] args){
        ConfigInput config = new ConfigInput(args[0]);                         // Reads config and parameter input
        ParameterInput parameter = new ParameterInput(args[1]);
        config.readFile();
        parameter.readFile();    

        Park newPark = new Park(config.getParkSize());                              // Construct a park object based on park size in input file

        templates = config.returnTemplates();                                       // Static list of templates/randomGaussians is specified by input file
        randomGaussians = config.returnRandomGenerators();

        // Variable for total space of all rides specified in input
        int checkSpace = 0;    

        // Determine how many objects are wanted per template
        for(RideTemplate template: templates){
            int numberOfObjects = parameter.getNumber(template.getType());             
            template.setNumberOfObjects(numberOfObjects);                       // Number of objects is set per template
            checkSpace = checkSpace + template.getSpace()*numberOfObjects;
        }

        // If total space of rides specified in input is greater than park size, terminate program
        if(checkSpace>config.getParkSize()){                   
            System.out.println("Too many rides");
            System.exit(1);
        }

        // Rides are added to the park based on template info and number of objects 
        for(RideTemplate template: templates){
            for(int i =0; i<template.getNumberOfObjects();i++){
                newPark.addRide(new Ride(template));                          
            }
        }

        // Initialize new Controller. Get seed and percentage info
        Controller c = new Controller(newPark);                
        c.setSeed(config);
        c.setPercentage(config);

        // The simulation runs for 1000 timesteps
        while(timeStep<=1000){
            System.out.println();
            System.out.println("Step " + timeStep);
            c.peopleArriving();                             // Simulate people arriving
            c.parkSimulate();                               // Park simulate
            //c.park.printStats();
            timeStep++;      
        }

        System.out.println("");
        System.out.println("THE END");
        System.out.println("");
        System.out.println("Ride Types");
        for(RideTemplate template: templates){
            System.out.println("");
            template.printTemplate();
        }
        System.out.println("");
        c.park.printParkRides();

        System.out.println("Final Stats");
        c.park.printStats();
    }
}