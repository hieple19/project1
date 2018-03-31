
/**
 * Family class is a child class of Group class.
 * 
 * @author Hiep Le
 */

public class Family extends Group{

    /**
     * Class constructor takes in two Random Gaussian for child number and age
     * @param randomNumberChild Random Gaussian objects for child number.
     * @param randomAgeChild Random Gaussian objects for child age.
     */
    public Family(RandomGaussian randomNumberChild, RandomGaussian randomAgeChild){
        super();

        int childNumber = randomNumberChild.getGaussian();
        while(childNumber == 0){                                
            childNumber = randomNumberChild.getGaussian();      // Ensure at least one child
        }

        Person mom = new Person(30);                       // Default age for two parents in family
        Person dad = new Person(30);

        this.members.add(mom);
        this.members.add(dad);

        for(int i = 0; i<childNumber; i++){
            Person newChild = new Person(randomAgeChild.getGaussian());     // Add child of random age no older than 18
            while(newChild.getAge() > 18){
                newChild = new Person(randomAgeChild.getGaussian());
            }
            this.members.add(newChild);
        }
        this.groupMemberTypes();                            // Update info about member types and min age
        this.groupMinAge();
    }

    /**
     * Class constructor with specific child number.
     * Child age is 10 by default.
     * For testing purposes.
     * @param childNumber number of children
     */
    public Family(int childNumber){
        super();
        Person mom = new Person(30);
        Person dad = new Person(30);
        this.members.add(mom);
        this.members.add(dad);

        for(int i = 0; i<childNumber; i++){
            this.members.add(new Person(10));
        }
        this.groupMemberTypes();
        this.groupMinAge();
    }

    public String getGroupType(){
        return "Family";
    }
}