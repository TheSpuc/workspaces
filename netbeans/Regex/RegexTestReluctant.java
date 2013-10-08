import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTestReluctant {
	public static final String text = "This string is my small example string";

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("T.*g");
		Matcher matcher = pattern.matcher(text);
		matcher.find();
		System.out.println(matcher.group());

		pattern = Pattern.compile("T.*?g");
		matcher = pattern.matcher(text);
		matcher.find();
		System.out.println(matcher.group());

		System.out.println(text.matches("T.*g"));
		System.out.println(text.matches("T.*?g"));
	}
}
