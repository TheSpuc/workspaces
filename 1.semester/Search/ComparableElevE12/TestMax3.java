package ComparableElevE12;


public class TestMax3 {

	public static void main(String[] args) {
		
		String str1="Preben"; 
		String str2="Anders"; 
		String str3="Thomas"; 
		
		String maxStr=Max3.MaxAf3String(str1,str2,str3);
		System.out.println(maxStr);
		
		
		Integer int1=new Integer(6);
		Integer int2=new Integer(20);
		Integer int3=new Integer(13);
		
		Integer maxInt=Max3.MaxAf3Integer(int1,int2,int3);
		System.out.println(maxInt);
		
		
		Double doub1=new Double(64.0);
		Double doub2=new Double(20.5);
		Double doub3=new Double(47.75);
		
		Double maxDoub=Max3.MaxAf3Double(doub1,doub2,doub3);
		System.out.println(maxDoub);

		// -------------------------------------------------------------
		
		Comparable comMax;
		
		comMax=Max3.MaxAf3Comparable(str1,str2,str3);
		System.out.println(comMax);
		
		comMax=Max3.MaxAf3Comparable(int1,int2,int3);
		System.out.println(comMax);
		
		comMax=Max3.MaxAf3Comparable(doub1,doub2,doub3);
		System.out.println(comMax);

		
		Ordre o1 = new Ordre(45,"Hurtig");
		Ordre o2 = new Ordre(23,"Langsom");
		Ordre o3 = new Ordre(40,"Mellem");
		
		comMax= Max3.MaxAf3Comparable(o1,o2,o3);
		System.out.println(comMax);
		
	}
}
