package testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ AllUnitTests.class, AllIntegrationTests.class})
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite();

		suite.addTestSuite(AllUnitTests.class);
		suite.addTestSuite(AllIntegrationTests.class);

		return suite;
	}

}
