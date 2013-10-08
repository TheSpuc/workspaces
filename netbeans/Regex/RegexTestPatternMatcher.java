import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexTestPatternMatcher {
	public static final String text = "This is my small example string";

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(text);
		// Check all occurance
		while (matcher.find()) {
			System.out.print("Start index: " + matcher.start());
			System.out.print(" End index: " + matcher.end() + " ");
			System.out.println(matcher.group());
		}
		// Now create a new pattern and matcher to replace whitespace with tabs

		Pattern replace = Pattern.compile("\\s+");
		Matcher matcher2 = replace.matcher(text);
		System.out.println(matcher2.replaceAll("\t"));
	}
}

