package com.example.androidUI;

import triage.Nurse;
import triage.Patient;
import triage.PatientNotFoundException;
import triage.Vitals;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RecordVitalsActivity extends Activity {
	
	/** Patient being updated*/
	private Patient patient;
	
	/** Nurse that is logged in*/
	private Nurse nurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_vitals);
		
		Intent intentV = getIntent();
		this.patient = (Patient) intentV.getSerializableExtra("patientKey");
		this.nurse = (Nurse) intentV.getSerializableExtra("nurseKey");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_vitals, menu);
		return true;
	}
	
	// When back button is pressed is passes back the changed object
	@Override
	public void onBackPressed(){
		Intent data = new Intent();
	    data.putExtra("resultNKey", this.nurse);
	    setResult(Activity.RESULT_OK, data);
	    super.onBackPressed();
	}

	/**
	 * Records Vitals data at a particular time for the currently accessed patient
	 * record.
	 * @param view
	 * @throws PatientNotFoundException
	 */
	public void recordVitals(View view) throws PatientNotFoundException{
		// Gets all input data from the Nurse.
		EditText tempText = (EditText) findViewById(R.id.tempText);
		EditText bpdText = (EditText) findViewById(R.id.bpDiastolicText);
		EditText bpsText = (EditText) findViewById(R.id.bpSystolicText);
		EditText heartText = (EditText) findViewById(R.id.heartRateText);
		TextView message = (TextView) findViewById(R.id.confirmMessage);


		// Checks to see if the Nurse forgot to input required data.
		if (tempText.length() == 0){
			tempText.setError("Must fill out required input");
			
		}
		
		if (bpdText.length() == 0){
			bpdText.setError("Must fill out required input");
			
		}
		
		if (bpsText.length() == 0){
			bpsText.setError("Must fill out required input");
			
		}
		
		if (heartText.length() == 0){
			heartText.setError("Must fill out required input");
			
		}
		
		else if (tempText.length() > 0 &&
				bpdText.length() > 0 &&
				bpsText.length() > 0){
			
			// Takes the input from nurse and changes them to their correct values
			double temp = Double.parseDouble(tempText.getText().toString());
			int bpd = Integer.parseInt(bpdText.getText().toString());
			int bps = Integer.parseInt(bpsText.getText().toString());
			int heart = Integer.parseInt(heartText.getText().toString());
			
			// creates vitals object and records it to the specified patient
			Vitals vitals = new Vitals(temp, bps, bpd, heart);
			this.patient = this.nurse.lookUpPatient(this.patient.getHealthCardNumber());
			this.nurse.recordVitals(this.patient, vitals);
			
			message.setText("Vitals was successfully recorded");
		}
		
	}
}
