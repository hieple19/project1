
/**
 * Individual class is a child class of Group class.
 * Object of class only has one element in list of members.
 * 
 * @author Hiep Le
 */

public class Individual extends Group{

    /**
     * Class constructor takes in a Random Gaussian for individual age
     * @param random gaussian object for individual age 
     */

    public Individual(RandomGaussian randomAgeIndividual){
        super();
        int age = randomAgeIndividual.getGaussian();
        Person individual = new Person(age);
        this.members.add(individual); 
        this.groupMemberTypes();
        this.groupMinAge();
    }

    /**
     * Class constructor with a specific age
     * @param age
     */
    public Individual(int age){
        super();
        Person person = new Person(age);
        this.members.add(person);
        this.groupMemberTypes();
        this.groupMinAge();
    }

    public String getGroupType(){
        return "Individual";
    }

}

    