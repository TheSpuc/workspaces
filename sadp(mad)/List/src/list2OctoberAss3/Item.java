package list2OctoberAss3;

public class Item<T> implements Comparable<Item<T>>  {
	
	private int priority;
	private T data;
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public void setData(T data){
		this.data = data;
	}
	
	public T getData(){
		return data;
	}
	
	@Override
	public int compareTo(Item<T> other){
		return priority-other.getPriority();
	}
}
