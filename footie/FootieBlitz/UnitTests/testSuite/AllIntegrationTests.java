package testSuite;

import integrationTests.ClubManipulationTest;
import integrationTests.LeagueManipulationTest;
import integrationTests.MatchTest;
import integrationTests.MessageTest;
import integrationTests.PlayerManipulation;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ClubManipulationTest.class, LeagueManipulationTest.class, 
	MatchTest.class, MessageTest.class, PlayerManipulation.class})
public class AllIntegrationTests extends TestCase{

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(ClubManipulationTest.class);
		suite.addTestSuite(LeagueManipulationTest.class);
		suite.addTestSuite(MatchTest.class);
		suite.addTestSuite(MessageTest.class);
		suite.addTestSuite(PlayerManipulation.class);
		//$JUnit-BEGIN$
		
		//$JUnit-END$
		return suite;
	}

}
