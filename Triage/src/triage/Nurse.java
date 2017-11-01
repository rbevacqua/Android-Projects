package triage;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;

public class Nurse extends Staff implements Serializable{

	/** Unique serial ID */
	private static final long serialVersionUID = -1280031338409276508L;

	/**Constructs a nurse */
	public Nurse(){}

	/** Adds a patient to emergencyRoom 
	 * @param patient
	 */
	public void recordPatient(Patient patient){
		this.emergencyRoom.addPatient(patient);
	}


	/**Returns the Queue of Patients that need to see a doctor
	 *  ordered by Urgency Score, based on the most up to date Urgency Scores 
	 *  @return doctorQueueByUrgency The Queue of Patients that need to see
	 *  		a doctor, ordered by Urgency Score. For patients with the same
	 *  		Urgency Score, patients are organized by arrival time
	 */
	public Queue<Patient> accessDoctorQueueByUrgency(){
		return this.emergencyRoom.generateDoctorQueueByUrgency();
	}

	/**
	 * Returns the Queue of Patients that need to see a doctor ordered by 
	 *  arrival time to the Emergency Room 
	 *  @return doctorQueueByArrival The Queue of Patients that need to see
	 *  		a doctor, ordered by arrival time
	 */
	public List<Patient> accessDoctorListByArrival(){
		return this.emergencyRoom.getDoctorListByArrival();
	}


	/**
	 * Adds a new vitals object to the end of that patients list of vitals
	 * @param patient The patient whose vitals need to be updated
	 * @param vitals The new vitals for that patient
	 */
	public void recordVitals(Patient patient, Vitals vitals){
		Report report = patient.getCurrentReport();
		report.addVitals(vitals);
	}

	/**
	 * Adds symptoms to the end of that patients map of symptoms
	 * @param patient The patient whose symptoms need to be updated
	 * @param symptoms The new symptoms for that patient
	 */
	public void recordSymptoms(Patient patient, String symptoms){
		Report report = patient.getCurrentReport();
		report.addSymptoms(symptoms);
	}

	/**
	 * Updates the patient's doctorDate to the current time.
	 * @param patient The patient who was visited by a doctor
	 * @throws PatientNotFoundException 
	 */
	public void recordDoctorVisit(Patient patient) throws PatientNotFoundException{
		
		Report report = patient.getCurrentReport();
		report.setDoctorDate();
		this.emergencyRoom.movePatientToSeenByDoctor(
						patient.getHealthCardNumber());
	}


}
