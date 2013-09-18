package schoolStudentCompareTreeMap;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class School {

	private String name;
	private Map<Integer, Student> students;

	public School(String name){
		this.name = name;
		students = new TreeMap<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, Student> getStudents(){
		return new TreeMap<>(students);
	}

	public void addStudent(Student st){
			students.put(st.getStudentNr(), st);
	}

	public void removeStudent(Student st){
			students.remove(st);
	}

	public double average(){
		double result = 0;
		int nrOfStudents = 0;
		Iterator<Entry<Integer, Student>> it = students.entrySet().iterator();
		while(it.hasNext()){
			Student s = it.next().getValue();
			Set<Integer> list = new HashSet<>(s.getGrades());
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
		Iterator<Entry<Integer, Student>> it = students.entrySet().iterator();
		boolean found = false;
		while(it.hasNext() && !found){
			Student temp = it.next().getValue();
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
