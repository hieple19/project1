
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ParameterInputTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ParameterInputTest
{   
    /**
     * Method tests if ride template's number of objects is read properly by checking with a test parameter file.
     */
    @Test
    public void testRead(){
        ParameterInput input1 = new ParameterInput("parameterInputTest.txt");
        input1.readFile();

        assertEquals("Number of roller coaster", 1, input1.getNumber("roller_coaster"));
        assertEquals("Number of ferris wheel", 1, input1.getNumber("ferris_wheel"));
        assertEquals("Number of carousel", 2, input1.getNumber("carousel"));
        assertEquals("Number of bumper cars", 0, input1.getNumber("bumper_cars"));

    }
}
