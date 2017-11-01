package com.example.androidUI;

import java.util.Queue;

import triage.Nurse;
import triage.Patient;
import triage.Staff;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class UrgencyListActivity extends Activity {

	/** Staff that is logged in*/
	private Staff user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_urgency_list);
		
		// Gets current database of patients 
		Intent intentU = getIntent();
		TextView textList = (TextView) findViewById(R.id.textList);
		this.user = (Staff) intentU.getSerializableExtra("staffKey");
		
		Queue<Patient> urgencyList = ((Nurse) this.user).accessDoctorQueueByUrgency();
		
		String urgencyListString = "";
		
		// Creates the string of waiting patients that will be displayed in the activity.
		for (Patient p : urgencyList){
			urgencyListString += 
			"Name: " + p.getName() + "\n" +
			"HCN: " + p.getHealthCardNumber() + "\n" +
			"Date of Birth: " + p.getDateofBirth().toString() + "\n" + 
			"Urgency Score: " + p.getCurrentReport().computeUrgencyScore() + "\n\n";
		}
		
		textList.setText(urgencyListString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.urgency_list, menu);
		return true;
	}

}
