import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTestGroups {
	public static final String text = "This string is my small example string";

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("(s.*g).*(s.*g)");
		Matcher matcher = pattern.matcher(text);
		matcher.find();
		System.out.println(matcher.group());
		System.out.println(matcher.group(1));
		System.out.println(matcher.group(2));
	}
}
