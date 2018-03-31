
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class IndividualTest.
 *
 * @author  Hiep Le
 */

public class IndividualTest
{   
    /**
     * A test method to test type of group is returned properly.
     */
    @Test
    public void typeTest(){
        Group test = new Individual(10);
        assertEquals("Individual test", test.getGroupType(), "Individual");
    }
}
