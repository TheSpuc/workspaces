public class RegexTestStrings {
	public static final String text = "This is my small example string";

	public static void main(String[] args) {
		System.out.println(text.matches("\\w.*"));
		String[] splitString = (text.split("\\s+"));
		System.out.println(splitString.length);
		for (String string : splitString) {
			System.out.println(string);
		}
		// Replace all whitespace with tabs
		System.out.println(text.replaceAll("\\s+", "\t"));
	}
}

