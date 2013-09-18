package schoolStudentComparableTreeSet;

import java.util.Comparator;


public class StudentComparator implements Comparator<Student> {


	public int compare(Student o1, Student o2) {
		int compare = 0;
		if(o1.getStudentNr() == o2.getStudentNr()){
			compare = 0;
		}else if(o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()) == 0){
			compare = o1.getStudentNr()-o2.getStudentNr();
		}else compare = o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
		return compare;
	}
}
