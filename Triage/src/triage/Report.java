package triage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;


public class Report implements Serializable {

	/** Unique serial ID*/
	private static final long serialVersionUID = 4761472535279835583L;
	
	/** A list of the patient's vitals*/
	private List<Vitals> vitals;
	
	/** A SortedMap of the patient's symptoms with the date recorded as keys*/
	private SortedMap<Date, String> symptoms;
	
	/** The patient's age*/
	private Integer patientAge;
	
	/** The patient's arrival time at the Emergency Room*/
	private Date arrivalTime;
	
	/** A constant set to doctorDate when the patient is still on the
	 * wait list (The patient has not seen the doctor yet)*/
	@SuppressWarnings("deprecation")
	private static Date NOTSEENDOCTOR = new Date(-100,0,01,0, 0);
	
	/** The date and time the patient has seen the doctor*/
	private Date doctorDate;

	/** A SortedMap of the patient's medication with associated
	 * instructions as values*/
	private SortedMap<String, String> prescriptions;
	
		
	/**
	 * Constructs a Report object and initializes the instance variables
	 * @param arrivalTime The patient's arrival time
	 * @param patientAge The patient's age
	 */
	public Report(Date arrivalTime, int patientAge) {
		this.arrivalTime = new Date();
		this.vitals = new ArrayList<Vitals>();
		this.symptoms = new TreeMap<Date, String>();
		this.patientAge = patientAge;
		this.doctorDate = NOTSEENDOCTOR;
		this.prescriptions = new TreeMap<String, String>();
	}

	/**
	 * Constructs a Report object and initializes the instance variables
	 * @param arrivalTime The patient's arrival time
	 * @param doctorDate The date and time the patient has seen the doctor
	 * (default is NOTSEENBYDOCTOR)
	 * @param patientAge The patient's age
	 */
	public Report(Date arrivalTime, Date doctorDate, int patientAge) {
		this.arrivalTime = new Date();
		this.vitals = new ArrayList<Vitals>();
		this.symptoms = new TreeMap<Date, String>();
		this.patientAge = patientAge;
		this.doctorDate = doctorDate;
		this.prescriptions = new TreeMap<String, String>();
	}
	
	/**
	 * Computes and Returns an int of the patient's urgency score
	 * @return An int of the patient's urgency score
	 */
	public int computeUrgencyScore() {
		int urgencyScore = 0;
		try {
			urgencyScore = getLastVitals().computeParticalUrgencyScore();
			
		} catch (NoLastVitalsException e) {
			// By catching this and doing nothing, a patient with no vitals
			// gets urgency score of 0
		}
		if (patientAge < 2) {
			urgencyScore += 1;
		}
		return urgencyScore;
	}
	
	/**
	 * Adds a Vitals object to the list of Vitals in this report
	 * @param vitals The patient's current vitals as a Vitals object
	 */
	public void addVitals(Vitals vitals) {
		this.vitals.add(vitals);
	}
	
	/**
	 * Adds a String of symptoms to the SortedMap of symptoms
	 * @param symptoms The patient's symptoms as a String object
	 */
	public void addSymptoms(String symptoms) {
		this.symptoms.put(new Date(), symptoms);
	}

	/**
	 * Adds a String of symptoms to the SortedMap of symptoms
	 * @param timeTaken The date and time taken (Default is the current time)
	 * @param symptoms The patient's symptoms as a String object
	 */
	public void addSymptoms(Date timeTaken, String symptoms) {
		this.symptoms.put(timeTaken, symptoms);
	}
	
	/**
	 * Adds the name of the medication as a key and instructions for
	 * that medication as the value
	 * @param medication The name of the medication the doctor assigns
	 * the patient
	 * @param instructions The instruction for the use of this medication
	 */
	public void addPrescription(String medication, String instructions) {
		this.prescriptions.put(medication, instructions);
	}
	
	/**
	 * Removes a medication from the SortedMap of prescriptions.
	 * Should only be called if the doctor added the wrong medication.
	 * @param medication The name of the medication the doctor assigns
	 */
	public void removePrescription(String medication) {
		this.prescriptions.remove(medication);
	}

	/**
	 * Sets the current time as the date and time the patient sees the doctor
	 */
	public void setDoctorDate() {
		this.doctorDate = new Date();
	}

	/**
	 * Returns a Date object of the doctorDate
	 * @return A Date object of the doctorDate
	 */
	public Date getDoctorDate() {
		return this.doctorDate;
	}

	/**
	 * Returns a list of the patient's vitals on this report
	 * @return A list of the patient's vitals on this report
	 */
	public List<Vitals> getVitals() {
		return this.vitals;
	}

	/**
	 * Returns a Vitals object of the last recorded vitals on this report
	 * @return A Vitals object of the last recorded vitals on this report
	 * @throws NoLastVitalsException
	 */
	public Vitals getLastVitals() throws NoLastVitalsException {
		if (this.vitals.isEmpty()) {
			throw new NoLastVitalsException();
		}
		else {
			return this.vitals.get(this.vitals.size()-1);
		}
	}

	/**
	 * Returns a SortedMap of the patient's symptoms on this report
	 * @return A SortedMap of the patient's symptoms on this report
	 */
	public SortedMap<Date, String> getSymptoms() {
		return this.symptoms;
	}

	/**
	 * Returns a string containing the last symptoms recorded on this report
	 * @return A string containing the last symptoms recorded on this report
	 * @throws NoLastSymptomsException
	 */
	public String getLastSymptoms() throws NoLastSymptomsException {
		if (this.symptoms.isEmpty()) {
			throw new NoLastSymptomsException();
		}
		else {	
			return this.symptoms.get(this.symptoms.lastKey());
		}
	}

	/**
	 * Returns a SortedMap of the patient's prescriptions on this report.
	 * This SortedMap will include name of medication as keys and associated
	 * instructions as values.
	 * @return A SortedMap of the patient's prescriptions on this report
	 */
	public SortedMap<String, String> getPrescriptions() {
		return this.prescriptions;
	}

	/** Returns a string representation for this Report, containing the
    *  string representations of all instance variables for this Report
    * @return The string representation for this Report
    */
    public String toString() {
           String vitalsString = "";
           for (Vitals v : this.vitals) {
                  vitalsString += "###" + v.toString();
           }
           String symptomsString = symptoms.toString();
           String prescriptionsString = prescriptions.toString();
          
           return this.arrivalTime.toString() + "$$$" + 
           		this.doctorDate.toString() + "$$$" + this.patientAge +
           		"$$$" +  vitalsString + "$$$" + symptomsString + "$$$" +
           		prescriptionsString;
    }

	
}