package com.example.androidUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	/**This keeps track of current registered nurses within this activity*/
	private Map<String,String> registeredNurses;
	
	/**This keeps track of the current registered doctors within this activity*/
	private Map<String,String> registeredDoctors;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//Map that holds username keys and password values for nurses.
		this.registeredNurses = new HashMap<String,String>();
		
		//Map That holds username keys and password values for doctors.
		this.registeredDoctors = new HashMap<String,String>();
		
		try {
			// writes the usernames and passwords used by the TA
			FileOutputStream out_doctor = openFileOutput("doctor_passwords.txt",
					Context.MODE_APPEND );
			out_doctor.write(( "TADoctor" + ", " + "phase3" + "\n").getBytes());
			
			out_doctor.close();
			
			FileOutputStream out = openFileOutput("nurse_passwords.txt",
					Context.MODE_APPEND );
			out.write(( "TANurse" + ", " + "phase3" + "\n").getBytes());
			
			out.close();
			
			//loads previous registered nurses from a File
			FileInputStream in = openFileInput("nurse_passwords.txt");
			Scanner scanner = new Scanner(in);
			String[] record;
			while(scanner.hasNextLine()){
				record = scanner.nextLine().split(", ");
				this.registeredNurses.put(record[0], record[1]);
			}
			
			scanner.close();
			
			//loads registered doctors from a file
			FileInputStream in_doctor = openFileInput("doctor_passwords.txt");
			Scanner scanner_doctor = new Scanner(in_doctor);
			String[] record_doctor;
			while(scanner_doctor.hasNextLine()){
				record_doctor = scanner_doctor.nextLine().split(", ");
				this.registeredDoctors.put(record_doctor[0], record_doctor[1]);
			}
			
			scanner_doctor.close();
			
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
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void saveRegisteredNurse(View view){
		// Collect all View objects from current activity
		EditText username = (EditText)findViewById(R.id.usernameText);
		EditText password = (EditText)findViewById(R.id.pText);
		String user = username.getText().toString();
		String pass = password.getText().toString();
		TextView message = (TextView)findViewById(R.id.messageView);

		//produces error messages in the EditText View
		if (user.length() == 0){
			username.setError("must fill out username input," +
					" alphanumeric and case-sensitive");
			
		}
		
		if (pass.length() == 0){
			password.setError("must fill out password input," +
					" be alphanumeric and case-sensitive");
			
		}
		// adds the username to the registry of Nurses
		else if (!(registeredNurses.containsKey(user)) && user.length() > 0){
			message.setText("username was successfully registered");
			registeredNurses.put(user, pass);
			
			// Saves the new registered nurse
			try {
				FileOutputStream out = openFileOutput("nurse_passwords.txt",
						Context.MODE_APPEND );
				out.write(( user + ", " + pass + "\n").getBytes());
				
				out.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else if (registeredNurses.containsKey(user)){
			message.setText("This username is already taken");
		}
	}
	
	public void loginNurse(View view){
		// Collect all View objects from current activity
		EditText username = (EditText)findViewById(R.id.usernameText);
		EditText password = (EditText)findViewById(R.id.pText);
		String user = username.getText().toString();
		String pass = password.getText().toString();
		TextView message = (TextView)findViewById(R.id.messageView);
		
		// approving user and password with registered staff members
		boolean approvedNurse = registeredNurses.containsKey(user) && 
				registeredNurses.get(user).equals(pass);
		
		boolean approvedDoctor = registeredDoctors.containsKey(user) && 
				registeredDoctors.get(user).equals(pass);
		
		// logs in if the given username is associated with a given password
		if (approvedNurse || approvedDoctor){
			
			Intent intent = new Intent(this, HomepageActivity.class);
			
			//Transfer username,Nurse and emergencyRoom to next activity
			intent.putExtra("usernameKey", user);
		
			
			//Resets Views in current activity
			message.setText("");
			password.setText("");
			username.setText("");
			
			//Starts the next Activity
			startActivity(intent);
			
		}
		else{
			message.setText("username/password is not registered or incorrect");
		}
	}

}
