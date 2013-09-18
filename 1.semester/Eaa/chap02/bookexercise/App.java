package chap02.bookexercise;

public class App
{
	public static void main(String[] args)
	{
		Book b1 = new Book("Kölling", "Objects First");

		System.out.println(b1.getAuthor() + ": " + b1.getTitle());
	}
}
