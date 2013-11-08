package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class for modeling a ramp
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
@Table(name="ramps")
public class Ramp {

	@Id
	@GeneratedValue
	@Column(name="RAMP_ID")
	private int id;

	private int number;

	@OneToMany(mappedBy = "ramp")
	private List<Loading> loadings;

	@ManyToOne
	private PackageType packageType;


	/**
	 * JPA Constructor
	 */
	public Ramp(){}

	/**
	 * Constructor for the class Ramp
	 * @param number - number of the ramp
	 * @param packageType != null - packagetype of the ramp
	 */
	public Ramp(int number, PackageType packageType){
		this.number = number;
		loadings = new ArrayList<>();
		this.packageType = packageType;
		packageType.addRamp(this);
	}

	/**
	 * Method for getting the number
	 * @return int - representing the number
	 */
	public int getNumber(){
		return number;
	}

	/**
	 * Method for getting the package type
	 * @return PackageType
	 */
	public PackageType getPackageType(){
		return packageType;
	}

	/**
	 * Method for getting all loadings
	 * @return List<Loading> with all the loadings
	 */
	public List<Loading> getLoadings(){
		return new ArrayList<>(loadings);
	}

	/**
	 * Method for adding a new loading and sorting it 
	 * with insertion sort, for keeping the whole list sorted
	 * @param loading to be sorted in
	 */
	public void addLoading(Loading loading){
		if(!loadings.contains(loading)){
			int index = loadings.size();
			while(index > 0 && loadings.get(index-1).compareTo(loading) > 0){
				index--;
			}
			loadings.add(index, loading);
		}
		loading.setRamp(this);
	}

	/**
	 * Method for removing a loading
	 * @param loading to be removed
	 */
	void removeLoading(Loading loading){
		loadings.remove(loading);
		loading.setRamp(null);
	}

	//Search methods from here

	/**
	 * Method for finding the loading thats being loaded
	 * @return Loading with the state of beingLoaded
	 */
	public Loading getLoadingBeingLoaded(){
		List<Loading> list = getLoadingsInQueue();
		int index = list.size()-1;
		boolean found = false;
		Loading l = null;
		while(index >= 0 && !found){
			l = list.get(index);
			if(l.getState().equals(State.BEINGLOADED)){
				found = true;
			}
			index--;
		}
		return l;
	}

	/**
	 * Method for getting all the loadings that have 
	 * the state of being in queue
	 * @return List<Loading> with all loadings that have a state of queue
	 */
	public List<Loading> getQueueWithoutPriority(){
		List<Loading> result = new ArrayList<>();
		int index = loadings.size()-1;
		boolean found = false;
		while(index >= 0 && !found){
			Loading l = loadings.get(index);
			if(l.getState().equals(State.QUEUE)){
				result.add(l);
				index--;
			}else found = true;
		}
		return result;
	}

	/**
	 * Method for getting all loadings which dont 
	 * have the state done 
	 * @return List<Loading> with all loadings that dont have the state of being done
	 */
	public List<Loading> getLoadingsInQueue(){
		List<Loading> result = new ArrayList<>();
		boolean stateChanged = false;
		int index = loadings.size()-1;
		while(index >= 0 && !stateChanged){
			Loading l = loadings.get(index);
			State state = l.getState();
			if(state.equals(State.PRIORITY) || state.equals(State.BEINGLOADED) || state.equals(State.QUEUE)){
				result.add(l);
			}else stateChanged = true;
			index--;
		}
		Collections.reverse(result);
		return result;
	}

	/**
	 * Method for getting all loadings with the state
	 * beingloaded or priority 
	 * @return List<Loading> with all loadings that have the state of priority or beingloaded
	 */
	public List<Loading> getQueueWithPriority(){
		List<Loading> result = new ArrayList<>();
		boolean stateChanged = false;
		int index = loadings.size()-1;
		while(index >= 0 && !stateChanged){
			Loading l = loadings.get(index);
			State state = l.getState();
			if(state.equals(State.BEINGLOADED) || state.equals(State.PRIORITY)){
				result.add(l);
			}else stateChanged = true;
			index++;
		}
		return result;
	}

	/**
	 * toString method for the class Ramp
	 */
	@Override
	public String toString() {
		return number + ". " + packageType.toString();
	}

}
