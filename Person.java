/**
 * This class defines the Person class, the basis of Group Class
 * @author Hiep Le
 */

public class Person{

    /** 
     * Person class holds information about person'age
     */
    protected int age;
    private int defaultAge = 20;

    /**
     * Constructor method to create a Person object with assigned age.
     * @param age person's age
     */
    public Person(int age){
        if(age<0){
            this.age = this.defaultAge;
        }
        else{
            this.age = age;
        }
    }

    public int getAge(){
        return this.age;
    }
    
    
  /**
   * Accessor method to return the person's type based on age
   * @return the person's type.
   */
    public String getType(){
        if(age>=22){
            return "adult";
        }
        else if(age>=18 && age<22){
            return "young_adult";
        }
        else if(age>=13 && age <18){
            return "teenager";
        }
        else if(age>=5 && age<12){
            return "adolescent";
        }
        else{
            return "toddler";
        }
    }

}