package schoolStudentSet;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class School {

	private String name;
	private Set<Student> students;

	public School(String name){
		this.name = name;
		students = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Student> getStudents(){
		return new HashSet<>(students);
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
			Set<Integer> list = new HashSet<>();
			list.addAll(it.next().getGrades());
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
		Iterator<Student> it = students.iterator();
		boolean found = false;
		while(it.hasNext() && !found){
			Student temp = it.next();
			if(temp.getStudentNr() == studentNr){
				s = temp;
				found = true;
			}
		}
		return s;
	}
	
	public String toString(){
		return name + ", students: " + students;
	}
}
