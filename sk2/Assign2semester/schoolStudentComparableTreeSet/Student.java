package schoolStudentComparableTreeSet;

import java.util.ArrayList;
import java.util.List;

public class Student implements Comparable<Student> {
	
	private int studentNr;
	private String name;
	private List<Integer> grades;
	
	public Student(int studentNr, String name){
		this.studentNr = studentNr;
		this.name = name;
		grades = new ArrayList<>();
	}

	public int getStudentNr() {
		return studentNr;
	}

	public void setStudentNr(int studentNr) {
		this.studentNr = studentNr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addGrade(int grade){
		grades.add(grade);
	}
	
	public List<Integer> getGrades(){
		return new ArrayList<>(grades);
	}
	
	public int compareTo(Student other){
		return studentNr-other.getStudentNr();
	}

	public String toString(){
		return getStudentNr() + ": " + getName() + ", grades: " + getGrades();
	}
}
