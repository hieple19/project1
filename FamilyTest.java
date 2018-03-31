
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class FamilyTest.
 *
 * @author  Hiep Le
 */
public class FamilyTest
{   
    /**
     * A test method to test group constructs as intended.
     */
    @Test
    public void constructorTest(){
        Family test = new Family(2);
        assertEquals("Family test", test.size(), 4);
    }
    
    /**
     * A test method to test type of group is returned properly
     */
    @Test
     public void typeTest(){
        Family test = new Family(2);
        assertEquals("Family test", test.getGroupType(), "Family");
    }

}
