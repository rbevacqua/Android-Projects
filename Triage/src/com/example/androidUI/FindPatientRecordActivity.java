package com.example.androidUI;

import triage.Patient;
import triage.PatientNotFoundException;
import triage.Staff;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class FindPatientRecordActivity extends Activity {
	
	/** Staff that is logged in*/
	private Staff user;
	
	/** Patient that needs to be accessed*/
	private Patient patient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_patient_record);

		Intent intentF = getIntent();
		this.user = (Staff) intentF.getSerializableExtra("nurseKey");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_patient_record, menu);
		return true;
	}
	
	// Sends all modified data back to the HomepageActivity.
	@Override
	public void onBackPressed(){
		Intent data = new Intent();
	    data.putExtra("resultKey", this.user);
	    setResult(Activity.RESULT_OK, data);
	    super.onBackPressed();
	}
	
	//EmergencyRoom and Staff member collected from previous activity
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if(requestCode == 1 && resultCode == Activity.RESULT_OK){
	    	this.user = (Staff) data.getSerializableExtra("resultSKey");
	    }
	    
	}
	
	/**
	 * Finds a patient in the database and opens their record for the
	 * PatientFeaturesActivity. If patient doesn't exist a message is displayed.
	 * @param current view
	 */
	public void findPatient(View view) {
		
		// Gets input data from EditText view
		EditText hcnText = (EditText) findViewById(R.id.findHealthCardText);
		TextView message = (TextView) findViewById(R.id.findMessage);
		
		// Passes current database to the PatientFeaturesActivity.
		Intent intent = new Intent(this, PatientFeaturesActivity.class);
		
		try {
			this.patient = user.lookUpPatient(hcnText.getText().toString());
			intent.putExtra("patientKey", this.patient);
			intent.putExtra("nurseKey", this.user);
			message.setText("");
			startActivityForResult(intent,1);
			
		} catch (PatientNotFoundException e) {
			
			message.setText("Patient could not be Found");
		}
		
	}

}
