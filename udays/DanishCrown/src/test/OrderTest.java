package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.CardboardBox;
import model.Order;
import model.PackageType;
import model.PartialOrder;
import model.PlasticBox;
import model.XmasTree;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
public class OrderTest {

	private static PackageType xmasTree, plasticBox, cardboardBox;

	private static Calendar date1;
	private static Order o1;
	
	private static Calendar date2;
	private static Order o2;
	private static PartialOrder o2po1;
	private static List<PartialOrder> partialOrders2;
	
	private static Calendar date3;
	private static Order o3;
	private static PartialOrder o3po1;
	private static List<PartialOrder> partialOrders3;
	
	private static Calendar date4;
	private static Order o4;
	private static PartialOrder o4po1, o4po2;
	private static List<PartialOrder> partialOrders4;
	
	private static Calendar date5;
	private static Order o5;
	private static PartialOrder o5po1, o5po2, o5po3, o5po4, o5po5;
	private static List<PartialOrder> partialOrders5;


	@BeforeClass
	public static void setUp() throws Exception {
		xmasTree = XmasTree.getInstance();
		plasticBox = PlasticBox.getInstance();
		cardboardBox = CardboardBox.getInstance();

		// Order 1 (0 kg)
		date1 = new GregorianCalendar(2013, 05, 20);
		o1 = new Order(1, 0, 0, date1, cardboardBox);

		// Order 2 (1 kg)
		date2 = new GregorianCalendar(2013, 05, 19);
		o2 = new Order(2, 1, 0, date2, xmasTree);
		partialOrders2 = o2.getPartialOrders();
		// PartialOrder 2-1
		o2po1 = partialOrders2.get(0);

		//Order 3 (20000 kg)
		date3 = new GregorianCalendar(2013, 05, 25);
		o3 = new Order(3, 20000, 10, date3, plasticBox);
		partialOrders3 = o3.getPartialOrders();
		// PartialOrder 3-1
		o3po1 = partialOrders3.get(0);

		// Order 4 (40000 kg)
		date4 = new GregorianCalendar(2013, 11, 15);
		o4 = new Order(4, 40000, 40, date4, plasticBox);
		partialOrders4 = o4.getPartialOrders();
		// PartialOrder 4-1
		o4po1 = partialOrders4.get(0);
		// PartialOrder 4-2
		o4po2 = partialOrders4.get(1);

		// Order 5 (83117 kg)
		date5 = new GregorianCalendar(2013, 05, 15);
		o5 = new Order(5, 83117, 80, date5, cardboardBox);
		partialOrders5 = o5.getPartialOrders();
		// PartialOrder 4-1
		o5po1 = partialOrders5.get(0);
		// PartialOrder 4-2
		o5po2 = partialOrders5.get(1);
		// PartialOrder 4-3
		o5po3 = partialOrders5.get(2);
		// PartialOrder 4-4
		o5po4 = partialOrders5.get(3);
		// PartialOrder 4-5
		o5po5 = partialOrders5.get(4);

	}

	@Test
	public void testOrderNumberForAll() {
		//Order 1
		assertEquals(1, o1.getNumber(), 0);
		//Order 2
		assertEquals(2, o2.getNumber(), 0);
		//Order 3
		assertEquals(3, o3.getNumber(), 0);
		//Order 4
		assertEquals(4, o4.getNumber(), 0);
		//Order 5
		assertEquals(5, o5.getNumber(), 0);
	}

	@Test
	public void testOrderWeightForAll() {	
		//Order 1
		assertEquals(0, o1.getWeight(), 0);
		//Order 2
		assertEquals(1, o2.getWeight(), 0);
		//Order 3
		assertEquals(20000, o3.getWeight(), 0);
		//Order 4
		assertEquals(40000, o4.getWeight(), 0);
		//Order 5
		assertEquals(83117, o5.getWeight(), 0);
	}

	@Test
	public void testOrderPackageTypeForAll() {	
		//Order1
		assertEquals(cardboardBox, o1.getPackageType());
		//Order2
		assertEquals(xmasTree, o2.getPackageType());
		//Order3
		assertEquals(plasticBox, o3.getPackageType());
		//Order4
		assertEquals(plasticBox, o3.getPackageType());
		//Order5
		assertEquals(cardboardBox, o1.getPackageType());
	}

	@Test
	public void testOrderQuantityOfPartialOrdersForAll() {	
		//Order1
		assertEquals(0, o1.getPartialOrders().size(), 0);
		//Order2
		assertEquals(1, o2.getPartialOrders().size(), 0);
		//Order3
		assertEquals(1, o3.getPartialOrders().size(), 0);
		//Order4
		assertEquals(2, o4.getPartialOrders().size(), 0);
		//Order5
		assertEquals(5, o5.getPartialOrders().size(), 0);
	}
	
	@Test
	public void test() {
		//Order1
		assertNotSame(1, o1.getPartialOrders().size());
		assertNotSame(date2, o1.getDate());
		//Order2
		assertNotSame(2, o2.getPartialOrders().size());
		assertNotSame(date3, o2.getDate());
		//Order3
		assertNotSame(2, o3.getPartialOrders().size());
		assertNotSame(date4, o3.getDate());
		//Order4
		assertNotSame(3, o4.getPartialOrders().size());
		assertNotSame(date5, o4.getDate());
		//Order5
		assertNotSame(6, o5.getPartialOrders().size());
		assertNotSame(date1, o5.getDate());
	}

	@Test
	public void testPartialOrderNumber() {
		//Order1
		// No PartialOrder created, because the weight is 0
		//Order2
		//PartialOrder 2-1
		assertEquals("2-1", o2po1.getPartialOrderNumber());
		//Order3
		//PartialOrder 3-1
		assertEquals("3-1", o3po1.getPartialOrderNumber());
		//Order4
		//PartialOrder 4-1
		assertEquals("4-1", o4po1.getPartialOrderNumber());
		//PartialOrder 4-2
		assertEquals("4-2", o4po2.getPartialOrderNumber());
		//Order5
		//PartialOrder 5-1
		assertEquals("5-1", o5po1.getPartialOrderNumber());
		//PartialOrder 5-2
		assertEquals("5-2", o5po2.getPartialOrderNumber());
		//PartialOrder 5-3
		assertEquals("5-3", o5po3.getPartialOrderNumber());
		//PartialOrder 5-4
		assertEquals("5-4", o5po4.getPartialOrderNumber());
		//PartialOrder 5-5
		assertEquals("5-5", o5po5.getPartialOrderNumber());
	}

	@Test
	public void testPartialOrderWeight() {
		//Order1
		// No PartialOrder created, because the weight is 0
		//Order2
		//PartialOrder 2-1
		assertEquals(1, o2po1.getWeight(), 0);
		//Order3
		//PartialOrder 3-1
		assertEquals(20000, o3po1.getWeight(), 0);
		//Order4
		//PartialOrder 4-1
		assertEquals(20000, o4po1.getWeight(), 0);
		//PartialOrder 4-2
		assertEquals(20000, o4po2.getWeight(), 0);
		//Order5
		//PartialOrder 5-1
		assertEquals(20000, o5po1.getWeight(), 0);
		//PartialOrder 5-2
		assertEquals(20000, o5po2.getWeight(), 0);
		//PartialOrder 5-3
		assertEquals(20000, o5po3.getWeight(), 0);
		//PartialOrder 5-4
		assertEquals(20000, o5po4.getWeight(), 0);
		//PartialOrder 5-5
		assertEquals(3117, o5po5.getWeight(), 0);
	}

	public void testPartialOrderDate() {
		//Order1
		// No PartialOrder created, because the weight is 0
		//Order2
		//PartialOrder 2-1
		assertEquals(date2, o2po1.getDate());
		//Order3
		//PartialOrder 3-1
		assertEquals(date3, o3po1.getDate());
		//Order4
		//PartialOrder 4-1
		assertEquals(date4, o4po1.getDate());
		//PartialOrder 4-2
		assertEquals(date4, o4po2.getDate());
		//Order5
		//PartialOrder 5-1
		assertEquals(date5, o5po1.getDate());
		//PartialOrder 5-2
		assertEquals(date5, o5po2.getDate());
		//PartialOrder 5-3
		assertEquals(date5, o5po3.getDate());
		//PartialOrder 5-4
		assertEquals(date5, o5po4.getDate());
		//PartialOrder 5-5
		assertEquals(date5, o5po5.getDate());
	}
	
	@Test
	public void testPartialOrderMargin() {
		//Order1
		// No PartialOrder created, because the weight is 0
		//Order2
		//PartialOrder 2-1
		assertEquals(0, o2po1.getMargin(), 0);
		//Order3
		//PartialOrder 3-1
		assertEquals(10, o3po1.getMargin(), 0);
		//Order4
		//PartialOrder 4-1
		assertEquals(40, o4po1.getMargin(), 0);
		//PartialOrder 4-2
		assertEquals(40, o4po2.getMargin(), 0);
		//Order5
		//PartialOrder 5-1
		assertEquals(80, o5po1.getMargin(), 0);
		//PartialOrder 5-2
		assertEquals(80, o5po2.getMargin(), 0);
		//PartialOrder 5-3
		assertEquals(80, o5po3.getMargin(), 0);
		//PartialOrder 5-4
		assertEquals(80, o5po4.getMargin(), 0);
		//PartialOrder 5-5
		assertEquals(80, o5po5.getMargin(), 0);
	}
	
	@Test
	public void testPartialOrderPackageType() {
		//Order1
		// No PartialOrder created, because the weight is 0
		//Order2
		//PartialOrder 2-1
		assertEquals(xmasTree, o2po1.getPackageType());
		//Order3
		//PartialOrder 3-1
		assertEquals(plasticBox, o3po1.getPackageType());
		//Order4
		//PartialOrder 4-1
		assertEquals(plasticBox, o4po1.getPackageType());
		//PartialOrder 4-2
		assertEquals(plasticBox, o4po2.getPackageType());
		//Order5
		//PartialOrder 5-1
		assertEquals(cardboardBox, o5po1.getPackageType());
		//PartialOrder 5-2
		assertEquals(cardboardBox, o5po2.getPackageType());
		//PartialOrder 5-3
		assertEquals(cardboardBox, o5po3.getPackageType());
		//PartialOrder 5-4
		assertEquals(cardboardBox, o5po4.getPackageType());
		//PartialOrder 5-5
		assertEquals(cardboardBox, o5po5.getPackageType());
	}

}
