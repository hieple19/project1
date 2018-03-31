
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test case class for Person class
 *
 * @author  Hiep Le
 */
public class PersonTest
{  
    /**
     * A test method to test age of Person object constructed
     */
    @Test
    public void testAge(){
        Person p = new Person(25);
        Person p2 = new Person(0);
        Person p3 = new Person(-4);

        assertEquals("Normal Test", p.getAge(), 25);
        assertEquals("Boundary Test", p2.getAge(),0);
        assertEquals("Invalid Test", p3.getAge(), 20);
    }
    
    /**
     * A test method to test type of Person object constructed
     */
    @Test
    public void testType(){
        Person p = new Person(25);
        Person p2 = new Person(18);
        Person p3 = new Person(13);
        Person p4 = new Person(5);
        Person p5 = new Person(0);
        Person p6 = new Person(-4);

        assertEquals("Adult Test", p.getType(), "adult");
        assertEquals("Young Adult Test", p2.getType(),"young_adult");
        assertEquals("Teenager Test", p3.getType(), "teenager");
        assertEquals("Adolescent Test", p4.getType(), "adolescent");
        assertEquals("Toddler Test", p5.getType(), "toddler");
        assertEquals("Default Test", p6.getType(), "young_adult");
    }
}
