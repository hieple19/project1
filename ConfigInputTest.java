
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
/**
 * The test class ConfigInputTest.
 *
 * @author  Hiep Le
 */
public class ConfigInputTest
{   
    /**
     * Method tests if group's percentage is read properly by checking with a test parameter file.
     */
    @Test
    public void testInfo(){
        ConfigInput input1 = new ConfigInput("configTest.txt");

        input1.readFile();

        assertEquals("Park size", 650, input1.getParkSize());
        assertEquals("Individual Percentage ", 0.30, input1.getIndividualPercentage(), 0.01);
        assertEquals("Friends Percentage", 0.33, input1.getFriendPercentage(), 0.01);
        assertEquals("Family Percentage", 0.37, input1.getFamilyPercentage(), 0.01);
        assertEquals("Seed", 10005, input1.getSeed());
    }

    /**
     * Method tests if template info is read properly by checking with a test parameter file.
     */
    @Test
    public void testTemplate(){
        ConfigInput input1 = new ConfigInput("configTest.txt");
        input1.readFile();

        ArrayList<RideTemplate> result = input1.returnTemplates();

        ArrayList<String> appealGroups = new ArrayList<String>();
        appealGroups.add("adult");
        appealGroups.add("young_adult");
        appealGroups.add("teenager");  

        RideTemplate template1 = new RideTemplate("roller_coaster", 80,3,20,120,appealGroups);

        appealGroups.clear();
        appealGroups.add("adolescent");
        appealGroups.add("toddler");

        RideTemplate template2 = new RideTemplate("carousel", 40,5,35,50,appealGroups);

        ArrayList<RideTemplate> expected = new ArrayList<RideTemplate>();
        expected.add(template1);
        expected.add(template2);

        assertEquals("Template 1", true, template1.compareTo(result.get(0)));
        assertEquals("Template 2", true, template2.compareTo(result.get(1)));
    }

    /**
     * Method tests if gaussian generators info is read properly by checking with a test parameter file.
     */
    @Test
    public void testGaussian(){
        ConfigInput input1 = new ConfigInput("configTest.txt");
        input1.readFile();

        ArrayList<RandomGaussian> randoms = input1.returnRandomGenerators();

        assertTrue(randoms.get(0).compareTo(new RandomGaussian(2,1,10005)));
        assertTrue(randoms.get(1).compareTo(new RandomGaussian(20,1.5,10005)));
        assertTrue(randoms.get(2).compareTo(new RandomGaussian(4,2,10005)));
        assertTrue(randoms.get(3).compareTo(new RandomGaussian(2,1,10005)));
        assertTrue(randoms.get(4).compareTo(new RandomGaussian(9,4,10005)));
        assertTrue(randoms.get(5).compareTo(new RandomGaussian(28,5,10005)));

    }
}

