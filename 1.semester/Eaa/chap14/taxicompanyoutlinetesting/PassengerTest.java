package chap14.taxicompanyoutlinetesting;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PassengerTest.
 *
 * @author  David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class PassengerTest
{
    /**
     * Default constructor for test class PassengerTest
     */
    public PassengerTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    protected void tearDown()
    {
    }

    /**
     * Test basic creation of a passenger.
     * Ensure that the pickup and destination locations
     * have been set.
     */
    @Test
	public void testCreation()
	{
		Location pickup = new Location();
		Location destination = new Location();
		Passenger passenger1 = new Passenger(pickup, destination);
		assertEquals(destination, passenger1.getDestination());
		assertEquals(pickup, passenger1.getPickupLocation());
	}
}


