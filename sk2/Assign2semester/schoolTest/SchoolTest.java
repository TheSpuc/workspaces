package schoolTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SchoolTest {

	private School s;
	private Student s1, s2;

	@Before
	public void setUp() throws Exception {
		s = new School("Eaaa");
		s1 = new Student(0, "Mark");
		s2 = new Student(1, "Vicki");

	}

	@Test
	public void test() {
		assertTrue(s1.getName() == "Mark");
		assertTrue(s2.getName() == "Vicki");

		assertTrue(s1.getStudentNr() == 0);
		assertTrue(s2.getStudentNr() == 1);

		s1.addGrade(10);
		s1.addGrade(2);

		assertTrue(s1.getGrades().contains(10));
		assertTrue(s1.getGrades().contains(2));

		s.addStudent(s1);
		assertEquals((10+2)/2, s.average(), 0.0);

		assertEquals(s1, s.findStudent(0));
	}

}
