package unitTest;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import helpers.BallPhysicsHelper;
import model.Bold;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BoldPhysicsTest {

	private Bold bold;
	
	
	@Before
	public void setUp(){
		bold = new Bold();
	}
	
	@Test
	public void testSpeedOnYAxisNoZ(){
		bold.setY(0);
		bold.setZ(0);
		bold.setX(0);
		bold.setA(0);
		bold.setZForce(0);
		bold.setCurl(0);
		bold.setSpeed(10);
		BallPhysicsHelper.calculatePhysics(bold);
		
		assertEquals(0, bold.getX(), 1e-3);
		assertEquals(0.3, bold.getY(), 1e-3);
		assertEquals(0, bold.getZ(), 1e-3);
		assertEquals(9.818, bold.getSpeed(), 1e-3);
		assertEquals(0, bold.getCurl(), 1e-3);
		assertEquals(0, bold.getA(), 1e-3);
	}
	
	@Test
	public void defaultXZeroSpeed(){
		bold.setY(0);
		bold.setZ(0);
		bold.setX(0);
		bold.setA(0);
		bold.setCurl(0);
		bold.setSpeed(10);
		BallPhysicsHelper.calculatePhysics(bold);
		
		assertEquals(0, bold.getX(), 1e-3);
		assertEquals(0.3, bold.getY(), 1e-3);
		assertEquals(0, bold.getZ(), 1e-3);
		assertEquals(9.818, bold.getSpeed(), 1e-3);
		assertEquals(0, bold.getCurl(), 1e-3);
		assertEquals(0, bold.getA(), 1e-3);
	}
	
	@Test
	public void testsfghDirection(){
		bold.setY(0);
		bold.setZ(0);
		bold.setX(0);
		bold.setA(0);
		bold.setZForce(0);
		bold.setCurl(0);
		bold.setSpeed(10);
		BallPhysicsHelper.calculatePhysics(bold);
		
		assertEquals(0, bold.getX(), 1e-3);
		assertEquals(0.3, bold.getY(), 1e-3);
		assertEquals(0, bold.getZ(), 1e-3);
		assertEquals(9.818, bold.getSpeed(), 1e-3);
		assertEquals(0, bold.getCurl(), 1e-3);
		assertEquals(0, bold.getA(), 1e-3);
	}
	
	@Ignore
	public void testsfdgXDirection(){
		bold.setY(0);
		bold.setZ(0);
		bold.setX(0);
		bold.setA(0);
		bold.setZForce(0);
		bold.setCurl(0);
		bold.setSpeed(10);
		BallPhysicsHelper.calculatePhysics(bold);
		
		assertEquals(0, bold.getX(), 1e-3);
		assertEquals(0.3, bold.getY(), 1e-3);
		assertEquals(0, bold.getZ(), 1e-3);
		assertEquals(9.818, bold.getSpeed(), 1e-3);
		assertEquals(0, bold.getCurl(), 1e-3);
		assertEquals(0, bold.getA(), 1e-3);
	}
	
	@Ignore
	public void testXDiregfhjction(){
		bold.setY(0);
		bold.setZ(0);
		bold.setX(0);
		bold.setA(0);
		bold.setZForce(0);
		bold.setCurl(0);
		bold.setSpeed(10);
		BallPhysicsHelper.calculatePhysics(bold);
		
		assertEquals(0, bold.getX(), 1e-3);
		assertEquals(0.3, bold.getY(), 1e-3);
		assertEquals(0, bold.getZ(), 1e-3);
		assertEquals(9.818, bold.getSpeed(), 1e-3);
		assertEquals(0, bold.getCurl(), 1e-3);
		assertEquals(0, bold.getA(), 1e-3);
	}
}
