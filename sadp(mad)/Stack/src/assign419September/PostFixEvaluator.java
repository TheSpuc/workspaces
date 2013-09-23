package assign419September;

import stackpackage.ArrayStack;

public class PostFixEvaluator {

	public static void main(String[] args){
		String test = "12 2 5 + - 4 * 2 /";
		System.out.println(evaluate(test));
	}

	public static int evaluate(String input){
		String[] splitInput = input.split(" ");
		ArrayStack<Integer> stack = new ArrayStack<>(input.length());
		int result = -1;
		int i = 0;
		try{
			while(i < splitInput.length){
				String temp = splitInput[i];
				if(temp.matches("\\d+")){
					stack.push((Integer.parseInt(temp)));
				}else if(temp.equals("+")){
					int a = stack.pop();
					stack.push((stack.pop()+a));
				}else if(temp.equals("-")){
					int a = stack.pop();
					stack.push((stack.pop()-a));
				}else if(temp.equals("*")){
					int a = stack.pop();
					stack.push((stack.pop()*a));
				}else if(temp.equals("/")){
					int a = stack.pop();
					stack.push((stack.pop()/a));
				}
				i++;
			}
			result = stack.pop();

			if(!stack.isEmpty()){
				throw new RuntimeException();
			}
		}catch(Exception e){
			throw new RuntimeException();
		}

		return result;
	}
}

