package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class for modeling a loading between a truck register and a ramp
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
@Table(name="loadings")
public class Loading implements Comparable<Loading> {

	@Id
	@GeneratedValue
	@Column(name="LOADING_ID")
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar estimatedStart;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar estimatedFinish;
	private boolean controlWeightSuccess;
	
	@ManyToOne
	private TruckRegister truckRegister;
	
	@ManyToOne
	private Ramp ramp;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	
	/**
	 * JPA Constructor
	 */
	public Loading(){}

	
	/**
	 * Constructor for the class Loading
	 * @param estimatedStart - estimated start time for the loading
	 * @param estimatedFinish - estimated finish time for the loading
	 * @param truckRegister - the truckRegister that should be loaded
	 * @param ramp - the ramp the loading should be done on
	 */
	public Loading(Calendar estimatedStart, Calendar estimatedFinish, TruckRegister truckRegister, Ramp ramp){
		this.estimatedStart = estimatedStart;
		this.estimatedFinish = estimatedFinish;
		controlWeightSuccess = false;
		this.truckRegister = truckRegister;
		state = State.QUEUE;
		truckRegister.addLoading(this);
		setRamp(ramp);
	}

	/**
	 * Method for getting the state
	 * @return State of the loading
	 */
	public State getState(){
		return state;
	}
	
	/**
	 * Method for setting the state
	 * @param state - the state to set for the loading
	 */
	public void setState(State state){
		this.state = state;
	}
	
	/**
	 * Method for getting the information if the
	 * control weight have succeeded
	 * @return boolean representing if the control weight have succeeded
	 */
	public boolean isControlWeightSuccess(){
		return controlWeightSuccess;
	}

	/**
	 * Method for setting if the control weight 
	 * have succeeded or failed
	 * @param success - boolean representing if the control weight have succeed or not
	 */
	public void setControlWeightSuccess(boolean success){
		controlWeightSuccess = success;
	}
	
	/**
	 * Method for getting the estimated start time
	 * @return Calendar representing the start time
	 */
	public Calendar getEstimatedStart(){
		return estimatedStart;
	}
	
	/**
	 * Method for setting the estimated start time
	 * @param estimatedStart - Calendar representing the estimated start time
	 */
	public void setEstimatedStart(Calendar estimatedStart){
		this.estimatedStart = estimatedStart;
	}
	
	/**
	 * Method for getting the estimated finish time
	 * @return Calendar representing the finish time
	 */
	public Calendar getEstimatedFinish(){
		return estimatedFinish;
	}
	
	/**
	 * Method for setting the estimated finish time
	 * @param estimatedFinish - Calendar representing the estimated finish time
	 */
	public void setEstimatedFinish(Calendar estimatedFinish){
		this.estimatedFinish = estimatedFinish;
	}
	
	/**
	 * Method for getting the truck register
	 * @return TruckRegister 
	 */
	public TruckRegister getTruckRegister(){
		return truckRegister;
	}

	/**
	 * Method for upholding the association between ramp and loading,
	 * making certain that if a ramp is broken and we change the ramp, 
	 * we still uphold the association between loading and the new ramp,
	 * while removing the association between the old ramp.
	 * @param ramp
	 */
	public void setRamp(Ramp ramp){
		if(this.ramp != ramp){
			if(this.ramp != null){
				this.ramp.removeLoading(this);
			}
			this.ramp = ramp;
			if(ramp != null){
				ramp.addLoading(this);
			}
		}
	}
	
	/**
	 * Method for getting the ramp
	 * @return Ramp
	 */
	public Ramp getRamp(){
		return ramp;
	}
	
	/**
	 * Method for defining how two loadings should be compared.
	 * Here compared between the two estimated start times,
	 * using the compareTo method defined by Calendar. 
	 * @param o
	 */
	@Override
	public int compareTo(Loading o) {
		return estimatedStart.compareTo(o.getEstimatedStart());
	}

	/**
	 * toString method for the class Loading
	 */
	@Override
	public String toString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		return state + " Start: " + dateFormat.format(estimatedStart.getTime()) + ", Finish: " + dateFormat.format(estimatedFinish.getTime());
	}
}
