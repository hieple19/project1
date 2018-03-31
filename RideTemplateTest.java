
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class RideTemplateTest.
 *
 * @author  Hiep Le
 */
public class RideTemplateTest
{   
    /**
     * Method tests if ride template's number of objects is set properly.
     */
    @Test
    public void setObjectTest(){
        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        RideTemplate ride = new RideTemplate("roller_coaster",80,3,20,120, appealGroups);

        ride.setNumberOfObjects(2);
        assertEquals("Number of objects", ride.getNumberOfObjects(),2);
    }
}
