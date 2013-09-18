package biblioTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BibliotekTest {

	private Bibliotek b;
	
	@Before
	public void setUp() throws Exception {
		b = new Bibliotek();
	}

	@Test
	public void beregnBoede() {
		assertEquals(0, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-01"), false), 0.0);
		assertEquals(10, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-02"), false), 0.0);
		assertEquals(10, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-05"), false), 0.0);
		assertEquals(10, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-08"), false), 0.0);
		assertEquals(30, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-09"), false), 0.0);
		assertEquals(30, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-13"), false), 0.0);
		assertEquals(30, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-15"), false), 0.0);
		assertEquals(45, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-16"), false), 0.0);
		
		assertEquals(0, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-01"), true), 0.0);
		assertEquals(20, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-02"), true), 0.0);
		assertEquals(20, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-05"), true), 0.0);
		assertEquals(20, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-08"), true), 0.0);
		assertEquals(60, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-09"), true), 0.0);
		assertEquals(60, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-13"), true), 0.0);
		assertEquals(60, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-15"), true), 0.0);
		assertEquals(90, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2013-01-16"), true), 0.0);
		assertEquals(90, b.beregnBoede(DateUtil.createDate("2013-01-01"), DateUtil.createDate("2014-11-19"), true), 0.0);
	}
}
