package triage;

import java.io.Serializable;
import java.util.Date;

public class Vitals implements Serializable {

	/** Unique serial ID*/
	private static final long serialVersionUID = -4765678880601659109L;

	/** The date and time the vitals are taken*/
	private Date timeTaken;

	/** The patient's internal temperature*/
	private double temperature;

	/** The patient's systolic blood pressure*/
	private int systolicBP;

	/** The patient's diastolic blood pressure*/
	private int diastolicBP;

	/** The patient's heart rate*/
	private int heartRate;
	

	/**
	 * Constructs the Vitals object and initializes the instance variables
	 * @param temperature The patient's internal temperature
	 * @param systolicBP The patient's systolic blood pressure
	 * @param diastolicBP The patient's diastolic blood pressure
	 * @param heartRate The patient's heart rate
	 */
	public Vitals(double temperature, int systolicBP, int diastolicBP,
			int heartRate) {
		this.temperature = temperature;
		this.systolicBP = systolicBP;
		this.diastolicBP = diastolicBP;
		this.heartRate = heartRate;
		this.timeTaken = new Date();
	}

	/**
	 * Constructs the Vitals object and initializes the instance variables 
	 * @param timeTaken The time the vitals were taken
	 * (default is current time)
	 * @param temperature The patient's internal temperature
	 * @param systolicBP The patient's systolic blood pressure
	 * @param diastolicBP The patient's diastolic blood pressure
	 * @param heartRate The patient's heart rate
	 */
	public Vitals(Date timeTaken, double temperature, int systolicBP,
			int diastolicBP, int heartRate) {
		this.temperature = temperature;
		this.systolicBP = systolicBP;
		this.diastolicBP = diastolicBP;
		this.heartRate = heartRate;
		this.timeTaken = timeTaken;
	}
	
	/**
	 * Returns a Date object of timeTake
	 * @return A Date object of timeTake
	 */
	public Date getTimeTaken() {
		return this.timeTaken;
	}

	/**
	 * Returns a double of the temperature
	 * @return A double of the temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * Returns an integer of the systolicBP
	 * @return An integer of the systolicBP
	 */
	public int getSystolicBP() {
		return systolicBP;
	}

	/**
	 * Returns an integer of the diastolicBP
	 * @return An integer of the diastolicBP
	 */
	public int getDiastolicBP() {
		return diastolicBP;
	}

	/**
	 * Returns an integer of the heartRate
	 * @return An integer of the heartRate
	 */
	public int getHeartRate() {
		return heartRate;
	}
	
	/**
	 * Returns the urgency score based solely on the information given
	 * by the vitals
	 * @return The urgency score based solely on the information given
	 * by the vitals
	 */
	public int computeParticalUrgencyScore() {
		int partical = 0;
		
		if (this.temperature >= 39.0) {
			partical += 1;
		}
		if (this.systolicBP >= 140 | this.diastolicBP >= 90) {
			partical += 1;
		}
		if (this.heartRate <= 50 | this.heartRate >= 100) {
			partical += 1;
		}
		
		return partical;
	}
	
	/** Returns a string representation for this Report, containing the
    *  string representations of all instance variables for this Report
    * @return The string representation for this Report
    */
    public String toString() {
           return "(" + this.timeTaken.toString() + "," + this.temperature +
                        "," + this.systolicBP + "," + this.diastolicBP + "," +
                        this.heartRate + ")";     
    }
    
}