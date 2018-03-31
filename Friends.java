/**
 * Friend class is a child class of Group class.
 * 
 * @author Hiep Le
 */

public class Friends extends Group
{
    /**
     * Class constructor takes in two Random Gaussian for child number and age
     * @param randomNumberFriend Random Gaussian objects for friend number.
     * @param randomAgeFriend Random Gaussian objects for friend age.
     */
    public Friends(RandomGaussian randomNumberFriend, RandomGaussian randomAgeFriend)
    {
        super();
        int groupNumber = randomNumberFriend.getGaussian();
        while(groupNumber <= 1){
            groupNumber = randomNumberFriend.getGaussian();
        }

        for(int i = 0; i<groupNumber; i++){
            this.members.add(new Person(randomAgeFriend.getGaussian()));
        }
        this.groupMemberTypes();
        this.groupMinAge();

    }

    /**
     * Class constructor with default 5 friends of all types.
     * For testing purposes.
     */
    public Friends(){
        super();
        this.members.add(new Person(1));
        this.members.add(new Person(6));
        this.members.add(new Person(12));
        this.members.add(new Person(19));
        this.members.add(new Person(22));
        this.groupMemberTypes();
        this.groupMinAge();
    }

    public String getGroupType(){
        return "Friends";
    }
}
