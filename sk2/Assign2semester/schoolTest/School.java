package schoolTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class School {

	private String name;
	private List<Student> students;

	public School(String name){
		this.name = name;
		students = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents(){
		return new ArrayList<>(students);
	}

	public void addStudent(Student st){
		if(!students.contains(st)){
			students.add(st);
		}
	}

	public void removeStudent(Student st){
		if(students.contains(st)){
			students.remove(st);
		}
	}

	public double average(){
		double result = 0;
		int nrOfStudents = 0;
		Iterator<Student> it = students.iterator();
		while(it.hasNext()){
			List<Integer> list = it.next().getGrades();
			if(list.size() > 0){
				double temp = 0;
				for(Integer i : list){
					temp += i;
				}
				nrOfStudents++;
				result += (temp/list.size());
			}
		}
		if(nrOfStudents > 0){
			result/=nrOfStudents;
		}
		return result;
	}
	
	public Student findStudent(int studentNr){
		Student s = null;
		int i = 0;
		boolean found = false;
		while(i < students.size() && !found){
			Student temp = students.get(i);
			if(temp.getStudentNr() == studentNr){
				s = temp;
				found = true;
			}else i++;
		}
		return s;
	}
	
	public String toString(){
		return name + ", students: " + students;
	}
}
