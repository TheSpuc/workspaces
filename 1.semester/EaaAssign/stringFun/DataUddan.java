package stringFun;

public class DataUddan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String foo = "Datamatiker";
		String bar = "Uddannelse";

		System.out.println(foo.toUpperCase());
		System.out.println(bar.toLowerCase());
		System.out.println(foo + " " + bar);
		String foobar = ""+foo + " " +bar.toLowerCase();
		System.out.println(foobar);
		System.out.println(foobar.length());
		System.out.println(foo.substring(0,7));
		System.out.println("letter 3: " + bar.charAt(2) + " Letter 7: " + bar.charAt(6));
		System.out.println(bar.substring(2,7));
		String[] moo = foobar.split(" ");
		System.out.println(moo[1]);
	}

}
