package generic4September1;

public class Person<T extends Comparable<T>> implements Comparable<Person<T>> {

	private T name;
	
	public Person(T name){
		this.name = name;
	}
	
	public void setName(T name){
		this.name = name;
	}
	
	public T getName(){
		return name;
	}
	
	public int compareTo(Person<T> other){
		return name.compareTo(other.getName());
	}
	
	public String toString(){
		return name + "";
	}
}
