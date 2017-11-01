package com.example.androidUI;

import triage.Nurse;
import triage.Patient;
import triage.PatientNotFoundException;
import triage.Staff;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PatientFeaturesActivity extends Activity {

	/** List of Options for the accessed patient*/
	private ListView features;
	
	/** Patient that is being accessed*/
	private Patient patient;
	
	/** Staff that is logged in*/
	private Staff user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intentFeature = getIntent();
		this.patient = (Patient) intentFeature.getSerializableExtra("patientKey");
		this.user = (Staff) intentFeature.getSerializableExtra("nurseKey");
		
		// loads a different xml file based on who is logged in.A Nurse or
		// Doctor.
		if(this.user instanceof Nurse){
			setContentView(R.layout.activity_features_nurse);
		}
		else{
			setContentView(R.layout.activity_features_doctor);
		}
		
		this.features = (ListView) findViewById(R.id.featuresListView);
		this.recordData(getCurrentFocus());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.features, menu);
		return true;
	}
	
	// Sends all modified patient data back to the previous activity.
	@Override
	public void onBackPressed(){
		Intent data = new Intent();
	    data.putExtra("resultSKey", this.user);
	    setResult(Activity.RESULT_OK, data);
	    super.onBackPressed();
	}
	
	//Gets the modified data from other activities
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if(requestCode == 1 && resultCode == Activity.RESULT_OK){
	        this.user = (Staff) data.getSerializableExtra("resultNKey");
	    }
	    
	    else if(requestCode == 2 && resultCode == Activity.RESULT_OK){
	        this.user = (Staff) data.getSerializableExtra("resultNKey");
	    }
	    
	}
	
	/**
	 * Checks what feature is chosen in the ListView and starts
	 * the appropriate activity for the chosen feature
	 * @param view
	 */
	public void recordData(View view){
		
		features.setOnItemClickListener(new OnItemClickListener() {  
			
			// checks what item is selected on the activity
			@Override
	        public void onItemClick(AdapterView<?> adView, View view, 
	        		int position,long id)                               
			{            
				// This is ran when View Patient data is pressed
	            if(features.getItemAtPosition(position).equals("View Patient Data")) {                                                                                                                                                        
	                Intent i = new Intent(PatientFeaturesActivity.this, 
	                		PatientDataActivity.class); 
	               	                
	                i.putExtra("patientKey", PatientFeaturesActivity.this.patient);
	                
	                i.putExtra("staffKey", PatientFeaturesActivity.this.user);
	                
	                startActivity(i);     
	                
	            } 
	            
	            // This is ran when Record Vitals is pressed.
	            else if (features.getItemAtPosition(position).equals("Record Vitals")){
	            	Intent i = new Intent(PatientFeaturesActivity.this, 
	                		RecordVitalsActivity.class); 
	            	
	            	i.putExtra("patientKey", PatientFeaturesActivity.this.patient);
	            	
	            	i.putExtra("nurseKey", PatientFeaturesActivity.this.user);
	            	
	                startActivityForResult(i,1); 
	                
	            }
	            
	            // This is ran when Record Seen by Doctor is pressed.
	            else if (features.getItemAtPosition(position).equals("Record Seen by Doctor")){
	            	Context context = getApplicationContext();
	            	CharSequence text;
	            	text = "Patient is moved from " +
	        				"Waiting List to Seen by Doctor";
	        		int duration = Toast.LENGTH_LONG;
	        		Toast seenByDoctorToast;
	        		
	        		try {
	        			PatientFeaturesActivity.this.patient = PatientFeaturesActivity.this.user.lookUpPatient(
								PatientFeaturesActivity.this.patient.getHealthCardNumber());
	        			
						((Nurse) PatientFeaturesActivity.this.user).recordDoctorVisit(
								PatientFeaturesActivity.this.patient);	
						

					} catch (PatientNotFoundException e) {
						// TODO Auto-generated catch block
						text = "Patient did not move from " +
		        				"Waiting List to Seen by Doctor";
					}
	            	
	        		seenByDoctorToast = Toast.makeText(context, text, duration);
					seenByDoctorToast.show();
	            }
	            
	            // This is ran when Record Prescription is pressed
	            else if (features.getItemAtPosition(position).equals("Record Perscription")){
	            	Intent i = new Intent(PatientFeaturesActivity.this, 
	                		PerscriptionActivity.class); 
	            	
	            	i.putExtra("patientKey", PatientFeaturesActivity.this.patient);
	            	
	            	i.putExtra("doctorKey", PatientFeaturesActivity.this.user);
	            	
	                startActivityForResult(i,2); 
	                
	            }
	        }                                                                                                       
		});
	}

}
