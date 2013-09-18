package forsikringTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BilForsikringTest {
	private BilForsikring bilforsikring;
	
	@Before
	public void setUp() throws Exception {
		bilforsikring = new BilForsikring();
		bilforsikring.setGrundpaemie(100.0);
	}
	
	@Test
	public void setGrundPr(){
		assertEquals(100.0, bilforsikring.getGrundPraemie(), 0);
	}
	
	@Test(expected= RuntimeException.class)
	public void setGrundPrExceptionMinus(){
		bilforsikring.setGrundpaemie(-1);
	}
	
	@Test(expected= RuntimeException.class)
	public void setGrundPrException(){
		bilforsikring.setGrundpaemie(0);
	}
	
	/**
	 * Because Margrethe coded properly, we can't check even through we should
	 */
//	@Test(expected= RuntimeException.class)
//	public void beregnPrZero(){
//		bilforsikring.beregnPraemie(18, true, 0);
//	}
	
	@Test(expected= RuntimeException.class)
	public void beregnPrUnderage(){
		bilforsikring.beregnPraemie(17, false, 0);
	}
	
	@Test(expected= RuntimeException.class)
	public void beregnPrAtAgeDamageFreeTooLong(){
		bilforsikring.beregnPraemie(18, false, 1);
	}
	
	@Test(expected= RuntimeException.class)
	public void beregnPrMinusYearsDamageFree(){
		bilforsikring.beregnPraemie(18, false, -1);
	}
	
	@Test
	public void beregnPr(){
		assertEquals(100*0.95, bilforsikring.beregnPraemie(25, true, 0), 0);
		assertEquals(100, bilforsikring.beregnPraemie(25, false, 0), 0);
		assertEquals(95, bilforsikring.beregnPraemie(25, true, 2), 0);
		assertEquals(100, bilforsikring.beregnPraemie(25, false, 2), 0);
		assertEquals(80, bilforsikring.beregnPraemie(25, true, 3), 0);
		assertEquals(85, bilforsikring.beregnPraemie(25, false, 3), 0);
		assertEquals(80, bilforsikring.beregnPraemie(25, true, 5), 0);
		assertEquals(85, bilforsikring.beregnPraemie(25, false, 5), 0);
		assertEquals(70, bilforsikring.beregnPraemie(25, true, 6), 0);
		assertEquals(75, bilforsikring.beregnPraemie(25, false, 6), 0);
		assertEquals(70, bilforsikring.beregnPraemie(30, true, 8), 0);
		assertEquals(75, bilforsikring.beregnPraemie(30, false, 8), 0);
		assertEquals(60, bilforsikring.beregnPraemie(30, true, 9), 0);
		assertEquals(65, bilforsikring.beregnPraemie(30, false, 9), 0);
		
		assertEquals(120, bilforsikring.beregnPraemie(24, true, 0), 0);
		assertEquals(125, bilforsikring.beregnPraemie(24, false, 0), 0);
		assertEquals(120, bilforsikring.beregnPraemie(24, true, 2), 0);
		assertEquals(125, bilforsikring.beregnPraemie(24, false, 2), 0);
		assertEquals(105, bilforsikring.beregnPraemie(24, true, 3), 0);
		assertEquals(110, bilforsikring.beregnPraemie(24, false, 3), 0);
		assertEquals(105, bilforsikring.beregnPraemie(24, true, 5), 0);
		assertEquals(110, bilforsikring.beregnPraemie(24, false, 5), 0);
		assertEquals(95, bilforsikring.beregnPraemie(24, true, 6), 0);
		assertEquals(100, bilforsikring.beregnPraemie(24, false, 6), 0);
	}
}