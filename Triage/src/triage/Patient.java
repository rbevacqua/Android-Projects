package triage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient implements Serializable {

	/** Unique serial ID */
	private static final long serialVersionUID = -152872174080775367L;
	
	/** The patient's name */
	private String name;
	
	/**The patient's date of birth */
	private Date patientDOB;
	
	
	/**The patient's health card number */
	private String healthCardNumber;
	
	/** A list of reports which are each a visit to the emergency room */
	private List<Report> reports;


	/**
	 * Constructs a patient
	 * @param name The patient's name
	 * @param patientDOB The patient's date of birth 
	 * @param healthCardNumber The patient's health card number
	 */
	public Patient(String name, Date patientDOB, String healthCardNumber) {
		this.name = name;
		this.patientDOB = patientDOB;
		this.healthCardNumber = healthCardNumber;
		this.reports = new ArrayList<Report>();

	}

	/**
	 * Returns the patient's name
	 * @return name The patient's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the patient's date of birth
	 * @return patientDOB The patient's date of birth
	 */
	public Date getDateofBirth() {
		return patientDOB;
	}

	/**
	 * Returns the patient's health card number
	 * @return healthCardNumber The patient's health card number
	 */
	public String getHealthCardNumber() {
		return healthCardNumber;
	}

	/**
	 * Returns the latest report in the patient's list of reports
	 * @return
	 */
	public Report getCurrentReport(){
		return reports.get(reports.size() - 1); 
	}

	/**
	 * Returns a list of all that patient's reports
	 * @return reports A list of all that patient's reports
	 */
	public List<Report> getReports() {
		return reports;
	}

	/**
	 * Adds a report to the patient's list of reports
	 * @param report A report containing information about one of that 
	 * patients stays in the emergency room
	 */
	public void addReport(Report report) {
		this.reports.add(report);
	}
	
	
	/**
	 * Returns the patients age rounded down to the nearest year
	 * @return ageInYears The patients age rounded down to the nearest year
	 */
	public int getAge(){
		Date currentTime = new Date();
		long ageInMilliseconds = currentTime.getTime() - this.patientDOB.getTime();
		int ageInYears = (int) (ageInMilliseconds/1000/60/60/24/365.25);
		return ageInYears;
	}
	
	/** Returns a string representation for this Patient, containing the
     *  string representations of all reports for this patient
     * @return The string representation for this Patient
     */
    
	@Override
     public String toString(){
            String reportsString = "";
            for (Report r : this.reports) {
                   reportsString += r.toString() + "@@@";
            }            
            return this.name + "|||" + this.patientDOB.toString() + "|||" +
                         this.healthCardNumber + "|||" + reportsString;
           
     }
}
