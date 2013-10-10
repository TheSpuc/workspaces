package assign119September;

import java.util.NoSuchElementException;

public class ArrayStack<T> implements StackI<T> {
	
	private T[] stack;
	private int top;
	private static final int defaultCapacity = 50;
	
	public ArrayStack(){
		this(defaultCapacity);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayStack(int initialCapacity){
		stack = (T[]) new Object[initialCapacity];
		top = 0;
	}

	@Override
	public void push(T newEntry) {
		if (top < stack.length){
			stack[top] = newEntry;
			top++;
		}
		
	}

	@Override
	public T pop() {
		T data = null;
		if (isEmpty()){
			throw new NoSuchElementException();
		}
		else {
			top--;
			data = stack[top];
			stack[top] = null;
		}
		
		return data;
	}

	@Override
	public T peek() {
		T data = null;
		if (isEmpty()){
			throw new NoSuchElementException();
		}
		else {
			data = stack[top-1];
		}
		return data;
	}

	@Override
	public boolean isEmpty() {
		return top == 0;
	}

	@Override
	public void clear() {
		top = 0;
		
	}

}
