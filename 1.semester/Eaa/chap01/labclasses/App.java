package chap01.labclasses;

public class App
{
	public static void main(String[] args)
	{
		Student st1 = new Student("Bob Dole", "11");
		Student st2 = new Student("Bill Clinton", "12");

		LabClass lab = new LabClass(20);
		lab.enrollStudent(st1);
		lab.enrollStudent(st2);

		lab.printList();
	}
}
