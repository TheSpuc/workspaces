package stackpackage;

public class StackUse {

	public static <E> void reverse(E[] tabel) {
		StackI<E> stak = new ArrayStack<E>(tabel.length);
		//StackI<E> stak = new LinkedStack<E>();
		for (int i = 0; i < tabel.length; i++) {
			stak.push(tabel[i]);
		}
		int i = 0;
		while (!stak.isEmpty()) {
			tabel[i] = stak.pop();
			i++;
		}

	}

	public static boolean palindrom(String text){
		boolean palindrom = true;
		int halfLength = text.length()/2;

		StackI<Character> stack = new ArrayStack<>(halfLength);
		for(int i=0; i<halfLength; i++){
			stack.push(text.charAt(i));
		}

		int i = halfLength;
		if(text.length() % 2 != 0){
			i++;
		}

		while(i<text.length() && palindrom){
			if(stack.pop() != text.charAt(i)){
				palindrom = false;
			}else i++;
		}
		return palindrom;
	}

	public static void main(String[] args) {

		Integer[] tal = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		reverse(tal);
		for (int i = 0; i < tal.length; i++){
			System.out.print(tal[i] + " ");
		}
		
		String check = "ABBA";
		System.out.println("\n" + palindrom(check));
	}



}
