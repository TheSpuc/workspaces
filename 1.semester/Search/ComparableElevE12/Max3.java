package ComparableElevE12;


public class Max3


{

	public static String MaxAf3String(String s1, String s2, String s3) {
		String max=s1;
		if (max.compareTo(s2)<0) max=s2;
		if (max.compareTo(s3)<0) max=s3;
		
		return max;
	}

	public static Integer MaxAf3Integer(Integer i1, Integer i2, Integer i3) {
		Integer max=i1;
		if (max.compareTo(i2)<0) max=i2;
		if (max.compareTo(i3)<0) max=i3;
		
		return max;
	}

	public static Double MaxAf3Double(Double d1, Double d2, Double d3) {
		Double max=d1;
		if (max.compareTo(d2)<0) max=d2;
		if (max.compareTo(d3)<0) max=d3;
		
		return max;
	}
	public static Comparable MaxAf3Comparable(Comparable c1, Comparable c2, Comparable c3) {
		Comparable max=c1;
		if (max.compareTo(c2)<0) max=c2;
		if (max.compareTo(c3)<0) max=c3;
		
		return max;
	}
}
