package triage;

public class PatientNotFoundException extends Exception {

	/**
	 *  This exception gets thrown when a patient that does exist is being
	 *  looked up 
	 */
	private static final long serialVersionUID = -1035628973223864521L;

	public PatientNotFoundException(){
		super();
	}
}
