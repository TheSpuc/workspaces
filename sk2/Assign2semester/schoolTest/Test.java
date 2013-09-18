package schoolTest;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		School s = new School("Erhvervsakademi");
		Student s1 = new Student(1, "Vicki");
		s1.addGrade(12);
		s1.addGrade(12);
		s1.addGrade(12);
		s1.addGrade(12);
		Student s2 = new Student(2, "Henrik");
		s2.addGrade(10);
		s2.addGrade(10);
		s2.addGrade(10);
		s2.addGrade(10);
		Student s3 = new Student(3, "Simon");
		s3.addGrade(7);
		s3.addGrade(7);
		s3.addGrade(7);
		s3.addGrade(7);
		Student s4 = new Student(4, "Casper");
		s4.addGrade(4);
		s4.addGrade(4);
		s4.addGrade(4);
		s4.addGrade(4);
		Student s5 = new Student(5, "Hans");
		s5.addGrade(2);
		s5.addGrade(2);
		s5.addGrade(2);
		s5.addGrade(2);
		Student s6 = new Student(6, "Mark");
		s6.addGrade(0);
		s6.addGrade(0);
		s6.addGrade(0);
		s6.addGrade(0);

		s.addStudent(s1);
		s.addStudent(s2);
		s.addStudent(s3);
		s.addStudent(s4);
		s.addStudent(s5);
		s.addStudent(s6);

		System.out.println(s);
		System.out.println("Average: " + s.average());
		System.out.println("Find student 1: " + s.findStudent(1));
		System.out.println("Find student 8: " + s.findStudent(8));

		//		School sk = new School("Erhversakadamiet");
		//		Student s1 = new Student(1, "Stefan");
		//		Student s2 = new Student(2, "Niclas");
		//		Student s3 = new Student(3, "Henrik");
		//		s1.addGrade(4);
		//		s1.addGrade(2);
		//		s1.addGrade(7);
		//		s1.addGrade(-3);
		//		s2.addGrade(4);
		//		s2.addGrade(2);
		//		s2.addGrade(2);
		//		s2.addGrade(12);
		//
		//		sk.addStudent(s1);
		//		sk.addStudent(s2);
		//		sk.addStudent(s3);
		//		
		//		System.out.println(sk.average());
	}

}
