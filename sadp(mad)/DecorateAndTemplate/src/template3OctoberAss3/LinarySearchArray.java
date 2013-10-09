package template3OctoberAss3;

public class LinarySearchArray<E extends Comparable<E>> extends SearchPattern<E> {

	private E[] array;
	private int index;
	private boolean done;
	
	public LinarySearchArray(E[] array){
		this.array = array;
	}
	
	
	@Override
	protected void init() {
		index = 0;
		done = false;
	}

	@Override
	protected boolean isEmpty() {
		return done;
	}

	@Override
	protected E select() {
		return array[index];
	}

	@Override
	protected void split(E m) {
		index++;
		if(index == array.length){
			done = true;
		}
	}

}
