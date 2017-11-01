package com.example.androidUI;

import java.util.Date;

import triage.Nurse;
import triage.Patient;
import triage.PatientNotFoundException;
import triage.Report;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RecordPatientActivity extends Activity {

	/**Nurse that is logged in*/
	private Nurse nurse;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_patient);
		
		Intent intentN = getIntent();
		this.nurse = (Nurse) intentN.getSerializableExtra("nurseKey");
		
		TextView message = (TextView) findViewById(R.id.recordMessage);

		message.setText("");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_patient, menu);
		return true;
	}
	
	@Override
	// Sends objects back to previous activity
	public void onBackPressed(){
		Intent data = new Intent();
	    data.putExtra("resultKey", this.nurse);
	    setResult(Activity.RESULT_OK, data);
	    super.onBackPressed();
	}
	
	/**
	 * Records new Patient data to the database of patients that are
	 * still waiting to be seen by the doctor.
	 * @param view
	 */
	public void recordPatientToWait(View view) {
		// Takes input from user in this activity
		EditText name = (EditText) findViewById(R.id.nameText);
		EditText day = (EditText) findViewById(R.id.dayText);
		EditText month = (EditText) findViewById(R.id.monthText);
		EditText year = (EditText) findViewById(R.id.yearText);
		EditText healthCardNum = (EditText) findViewById(R.id.healthCardText);
		
		//Gets the View for Record Message
		TextView message = (TextView) findViewById(R.id.recordMessage);
		message.setText("");
		
		// Get the string of name and health card number
		String stringName = name.getText().toString();
		String stringHealthCardNum = healthCardNum.getText().toString();
		
		
		// checks to see if the user forgot to input required data.
		if (stringName.length() == 0){
			name.setError("Must fill out required input");
			
		}
		
		if (stringHealthCardNum.length() == 0){
			healthCardNum.setError("Must fill out required input");
			
		}
		
		if (day.length() == 0){
			day.setError("Must fill out required input");
			
		}

		if (month.length() == 0){
			month.setError("Must fill out required input");
			
		}
		
		try {
			if (nurse.lookUpPatient(stringHealthCardNum) != null){
				message.setText("There is already a Patient with this health " +
						"card number");
			}
		} catch (PatientNotFoundException e) {
			// TODO Auto-generated catch block
	
			if (year.length() == 0){
				year.setError("Must fill out required input");
				
			}
			
			else if (month.length() > 0 && 
					day.length() > 0 && 
					stringName.length() > 0 && 
					stringHealthCardNum.length() > 0){
				
				// Get the in of day,month, and year
				int integerDay = Integer.parseInt(day.getText().toString());
				int integerMonth = Integer.parseInt(month.getText().toString());
				int integerYear = Integer.parseInt(year.getText().toString());
				
				//Create Date object for Date of Birth
				@SuppressWarnings("deprecation")
				Date dateOfBirth = new Date(integerYear - 1900, integerMonth - 1, 
						integerDay);
				
				Date arrivalDate = new Date();
				
				Patient patient = new Patient(stringName, dateOfBirth, 
						stringHealthCardNum);
				
				int patientAge = patient.getAge();
				
				Report report = new Report(arrivalDate, patientAge);
				
				patient.addReport(report);
				
				this.nurse.recordPatient(patient);
				message.setText("Patient has successfully been recorded");
				
			}
		}
		
	}

}
