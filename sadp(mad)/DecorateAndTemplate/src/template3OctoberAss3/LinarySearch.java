package template3OctoberAss3;

import java.util.List;

public class LinarySearch<E extends Comparable<E>> extends SearchPattern<E> {

	private List<E> list;
	private int index;
	private boolean done;
	
	public LinarySearch(List<E> list){
		this.list = list;
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
		return list.get(index);
	}

	@Override
	protected void split(E m) {
		index++;
		if(index == list.size()){
			done = true;
		}
	}



}
