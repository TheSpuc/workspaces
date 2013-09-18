package schoolStudentMap;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class School {

	private String name;
	private Map<Integer, Student> students;

	public School(String name){
		this.name = name;
		students = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, Student> getStudents(){
		return new HashMap<>(students);
	}

	public void addStudent(Student st){
			students.put(st.getStudentNr(), st);
	}

	public void removeStudent(Student st){
			students.remove(st.getStudentNr());
	}

	public double average(){
		double result = 0;
		int nrOfStudents = 0;
		Iterator<Entry<Integer, Student>> it = students.entrySet().iterator();
		//Iterator<Student> testIterator = students.values().iterator(); //This should be enough for getting only the values.
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
		return students.get(studentNr);
	}
	
	public String toString(){
		return name + ", students: " + students;
	}
}
