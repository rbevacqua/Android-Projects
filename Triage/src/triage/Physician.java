package triage;

public class Physician extends Staff{

	/** Unique serial ID */
	private static final long serialVersionUID = -1280031338409276508L;

	/**Constructs a nurse */
	public Physician(){}
	
	/**
	 * Adds a prescription, which is a tuple of medication and instrutions, to the patients map of prescriptions.
	 * @param patient The patient whose prescription map needs to be updated.
	 * @param medication The mediation the patient is being put on.
	 * @param instructions The instrutions for the patient to take the medication.
	 */
	public void recordPrescription(Patient patient, String medication, String instructions){
		Report report = patient.getCurrentReport();
		report.addPrescription(medication, instructions);
	}

}
