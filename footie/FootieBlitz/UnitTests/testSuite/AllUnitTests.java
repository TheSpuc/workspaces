package testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import data.PlayerMoraleTest;
import data.TrainingRegimeTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ PlayerMoraleTest.class, TrainingRegimeTest.class})
public class AllUnitTests extends TestCase{

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(PlayerMoraleTest.class);
		suite.addTestSuite(TrainingRegimeTest.class);
		//$JUnit-BEGIN$
		
		//$JUnit-END$
		return suite;
	}

}