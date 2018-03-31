
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class FriendsTest.
 *
 * @author  Hiep Le
 */

public class FriendsTest
{
    /**
     * A test method to test group constructs as intended.
     */
    @Test
    public void constructorTest(){
        Friends test = new Friends();
        assertEquals("Friends test", test.size(), 5);
    }
    
    /**
     * A test method to test type of group is returned properly.
     */
    @Test
    public void typeTest(){
        Friends test = new Friends();
        assertEquals("Friends test", test.getGroupType(), "Friends");
    }

}
