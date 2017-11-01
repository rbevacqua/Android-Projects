package com.example.androidUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import triage.Nurse;
import triage.Physician;
import triage.Staff;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class HomepageActivity extends Activity {
	
	/** Staff that is logged in*/
	private Staff user;
	
	/** Output Stream for File*/
	private FileOutputStream out;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String user = (String) intent.getStringExtra("usernameKey");
		
		// Creates a Doctor or Nurse and loads a different xml file 
		// based on who logged in.
		if (user.equals("TADoctor")){
			this.user = new Physician();
			setContentView(R.layout.activity_homepage_doctor);
		}
		else{
			this.user = new Nurse();
			setContentView(R.layout.activity_homepage_nurse);
		}
		
		// Sets the hompage header
		TextView header = (TextView) findViewById(R.id.headerView);
		header.setText("Welcome\n\n" + user);
				
		
		
		try {
			out = openFileOutput("PatientData.txt",Context.MODE_APPEND);
			out.close();
			FileInputStream in = openFileInput("PatientData.txt");
			Scanner scanner = new Scanner(in);
			String record = "";
			while (scanner.hasNextLine()){
				record += scanner.nextLine();
			}
			
			scanner.close();

			//EmergencyRoom loadData method called here
			this.user.loadEmergencyRoom(record);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.homepage, menu);
		return true;
	}
	
	//EmergencyRoom and Staff member collected from previous activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if(requestCode == 1 && resultCode == Activity.RESULT_OK){
	        this.user = (Staff) data.getSerializableExtra("resultKey");
	    }
	    
	    if(requestCode == 2 && resultCode == Activity.RESULT_OK){
	    	this.user = (Staff) data.getSerializableExtra("resultKey");
	    }
	}
	
	/**
	 * Starts the RecordPatientActivity in order to create new Patient Records
	 * @param view
	 */
	public void recordPatient(View view){
		TextView message = (TextView) findViewById(R.id.saveMessage);
		
		Intent intentR = new Intent(this, RecordPatientActivity.class);
		intentR.putExtra("nurseKey", this.user);
		message.setText("");
		startActivityForResult(intentR,1);
	}
	
	/**
	 * Starts UrgencyListActivity to view patients who haven't seen the doctor.
	 * Ordered by decreasing urgency.
	 * 
	 * @param view
	 */
	public void viewPatientUrgencyList(View view){
		// Passes current database to the activity
		Intent intentU = new Intent(this, UrgencyListActivity.class);
		intentU.putExtra("staffKey", this.user);
		startActivity(intentU);
	}

	/**
	 * Starts the FindPatientActivity in order to find patients in the
	 * database by health card number.
	 * @param view
	 */
	public void accessPatient(View view){
		TextView message = (TextView) findViewById(R.id.saveMessage);
		
		Intent intentP = new Intent(this, FindPatientRecordActivity.class);
		intentP.putExtra("nurseKey", this.user);
		message.setText("");
		startActivityForResult(intentP,2);
	}
	
	/**
	 * Saves all recorded data that the user has entered during their
	 * current log-in session.
	 * @param view
	 */
	public void saveAllData(View view) {
		TextView message = (TextView) findViewById(R.id.saveMessage);
		
		//EmergencyRoom saveData method called here
		try {
			out = openFileOutput("PatientData.txt",Context.MODE_PRIVATE);
			String data = user.saveEmergencyRoom();
			out.write(data.getBytes());
			out.close();
			message.setText("Save Successful");
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
}
