package com.example.androidUI;

import triage.Patient;
import triage.PatientNotFoundException;
import triage.Physician;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PerscriptionActivity extends Activity {
	
	/** Patient being updated*/
	private Patient patient;
	
	/** Doctor that is logged in*/
	private Physician doctor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perscription);
		
		Intent intentPer = getIntent();
		this.patient = (Patient) intentPer.getSerializableExtra("patientKey");
		this.doctor = (Physician) intentPer.getSerializableExtra("doctorKey");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perscription, menu);
		return true;
	}
	
	// When back button is pressed it passes back the changed object
	@Override
	public void onBackPressed(){
		Intent data = new Intent();
		data.putExtra("resultNKey", this.doctor);
		setResult(Activity.RESULT_OK, data);
		super.onBackPressed();
	}
	
	/**
	 * Records Prescription data for the Patient that is 
	 * currently being accessed.
	 * @param view
	 * @throws PatientNotFoundException
	 */
	public void recordPerscription(View view) throws PatientNotFoundException{
		EditText medicineText = (EditText) findViewById(R.id.medicine_Text);
		EditText instructText = (EditText) findViewById(R.id.instruction_Text);
		TextView confirmText = (TextView) findViewById(R.id.perscription_message);
		
		String medicineName = medicineText.getText().toString();
		String instructionString = instructText.getText().toString();

		if(medicineText.length() == 0){
			medicineText.setError("Must Fill out required input");
			
		}
		
		if(instructText.length() == 0){
			instructText.setError("Must Fill out required input");
			
		}
		
		else if (medicineText.length() > 0){
			this.patient = this.doctor.lookUpPatient(this.patient.getHealthCardNumber());
			this.doctor.recordPrescription(this.patient, medicineName, 
					instructionString);
			
			confirmText.setText("Perscription has been successfully recorded");
			
		}
	}

}
