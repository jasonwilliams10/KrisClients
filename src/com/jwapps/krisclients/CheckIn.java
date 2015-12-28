package com.jwapps.krisclients;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CheckIn extends Activity {

	private EditText name;
	private EditText date;
	private EditText concern;
	private Spinner sessionLength;
	private Spinner massageType;
	private TextView massageStyleText;
	private Spinner massageStyle;
	private CheckBox contactInfo;
	
	private TextView addressText;
	private EditText address;
	private TextView phoneText;
	private EditText phone;
	private TextView mobilePhoneText;
	private EditText mobilePhone;
	private TextView emailText;
	private EditText email;
	
	private EditText notes;
	
	private String sDate;
	
	private DatabaseHandler dbHelper;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		dbHelper = new DatabaseHandler(this);
		dbHelper.open();

		name = (EditText) findViewById(R.id.name);
		date = (EditText) findViewById(R.id.date);
		concern = (EditText) findViewById(R.id.concern);
		contactInfo = (CheckBox) findViewById(R.id.contactInfo);
		addressText = (TextView) findViewById(R.id.addressText);
		address = (EditText) findViewById(R.id.address);
		phoneText = (TextView) findViewById(R.id.phoneText);
		phone = (EditText) findViewById(R.id.phone);
		mobilePhoneText = (TextView) findViewById(R.id.mobilePhoneText);
		mobilePhone = (EditText) findViewById(R.id.mobilePhone);
		emailText = (TextView) findViewById(R.id.emailText);
		email = (EditText) findViewById(R.id.email);
		massageStyleText = (TextView) findViewById(R.id.massageStyleText);
		notes = (EditText) findViewById(R.id.notes);
		
		addressText.setVisibility(View.GONE);
		address.setVisibility(View.GONE);
		phoneText.setVisibility(View.GONE);
		phone.setVisibility(View.GONE);
		mobilePhoneText.setVisibility(View.GONE);
		mobilePhone.setVisibility(View.GONE);
		emailText.setVisibility(View.GONE);
		email.setVisibility(View.GONE);
		
		// Get the current date
		Calendar c = Calendar.getInstance();
		sDate = (c.get(Calendar.MONTH) + 1) + "/"
				+ c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);

		date.setText(sDate);

		// Length of session provided
		sessionLength = (Spinner) findViewById(R.id.sessionLength);
		// Set up the Spinner entries
		List<String> sessionListString = new ArrayList<String>();
		sessionListString.add("Please Select One");
		sessionListString.add("5 Min");
		sessionListString.add("10 Min");
		sessionListString.add("15 Min");
		sessionListString.add("20 Min");
		sessionListString.add("30 Min");
		sessionListString.add("45 Min");
		sessionListString.add("1 Hour");
		sessionListString.add("1 Hour 15 Min");
		sessionListString.add("1 Hour 30 Min");
		sessionListString.add("1 Hour 45 Min");
		sessionListString.add("2 Hours");

		ArrayAdapter<String> sessionArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, sessionListString);
		sessionArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sessionLength.setAdapter(sessionArrayAdapter);
		// Set up a callback for the spinner
		sessionLength.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onNothingSelected(AdapterView<?> arg0) {
			}

			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				// Code that does something when the Spinner value changes
				
			}
		});

		// Type of massage (Table or Chair)
		massageType = (Spinner) findViewById(R.id.massageType);
		// Set up the Spinner entries
		List<String> massageTypeListString = new ArrayList<String>();
		massageTypeListString.add("Please Select One");
		massageTypeListString.add("Chair");
		massageTypeListString.add("Table");

		ArrayAdapter<String> massageTypeArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				massageTypeListString);
		massageTypeArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		massageType.setAdapter(massageTypeArrayAdapter);
		// Set up a callback for the spinner
		massageType.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onNothingSelected(AdapterView<?> arg0) {
			}

			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				// Code that does something when the Spinner value changes
				
				if (position == 0) {
					
					massageStyleText.setVisibility(View.VISIBLE);
					massageStyle.setVisibility(View.VISIBLE);
					
				} else if (position == 1) {
					
					massageStyleText.setVisibility(View.GONE);
					massageStyle.setVisibility(View.GONE);
					
				} else if (position == 2) {
					
					massageStyleText.setVisibility(View.VISIBLE);
					massageStyle.setVisibility(View.VISIBLE);
					
				}
				
			}
		});

		// Style of massage (Swedish, hot stone, etc).
		massageStyle = (Spinner) findViewById(R.id.massageStyle);
		// Set up the Spinner entries
		List<String> massageStyleListString = new ArrayList<String>();
		massageStyleListString.add("Please Select One");
		massageStyleListString.add("Swedish");
		massageStyleListString.add("Aromatherapy");
		massageStyleListString.add("Hot Stone");
		massageStyleListString.add("Deep Tissue");
		massageStyleListString.add("Shiatsu");
		massageStyleListString.add("Thai");
		massageStyleListString.add("Pregnancy");
		massageStyleListString.add("Reflexology");
		massageStyleListString.add("Sports");
		massageStyleListString.add("Back Massage");

		ArrayAdapter<String> massageStyleArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				massageStyleListString);
		massageStyleArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		massageStyle.setAdapter(massageStyleArrayAdapter);
		// Set up a callback for the spinner
		massageStyle.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onNothingSelected(AdapterView<?> arg0) {
			}

			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				// Code that does something when the Spinner value changes
				
			}
		});
		
	
		contactInfo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
				
			if (isChecked) {
				
				addressText.setVisibility(View.VISIBLE);
				address.setVisibility(View.VISIBLE);
				phoneText.setVisibility(View.VISIBLE);
				phone.setVisibility(View.VISIBLE);
				mobilePhoneText.setVisibility(View.VISIBLE);
				mobilePhone.setVisibility(View.VISIBLE);
				emailText.setVisibility(View.VISIBLE);
				email.setVisibility(View.VISIBLE);
				
				

			} else {
				
				addressText.setVisibility(View.GONE);
				address.setVisibility(View.GONE);
				phoneText.setVisibility(View.GONE);
				phone.setVisibility(View.GONE);
				mobilePhoneText.setVisibility(View.GONE);
				mobilePhone.setVisibility(View.GONE);
				emailText.setVisibility(View.GONE);
				email.setVisibility(View.GONE);
				
			}

		}
	});

	}
	
	public void submit(View v) {
		
		int clientAddressLen = address.length();
		int clientPhoneLen = phone.length();
		int clientMobilePhoneLen = mobilePhone.length();
		int clientEmailLen = email.length();
		int notesLen = notes.length();
		int concernLen = concern.length();
		int nameLen = name.length();
		
		String clientName = name.getText().toString();
		String clientConcern = concern.getText().toString();
		String length = sessionLength.getSelectedItem().toString();
		String type = massageType.getSelectedItem().toString();
		String style = massageStyle.getSelectedItem().toString();
		String notesStr = notes.getText().toString();
		
		
		if (nameLen == 0) {
			new AlertDialog.Builder(this).setTitle("Attention!").setMessage("You must enter a name before continuing.").setNeutralButton("Close", null).show(); 
		} else if (sessionLength.getSelectedItemPosition()== 0 ) {
			new AlertDialog.Builder(this).setTitle("Attention!").setMessage("You must select a session length before continuing.").setNeutralButton("Close", null).show(); 
		} else if (massageType.getSelectedItemPosition() == 0) {
			new AlertDialog.Builder(this).setTitle("Attention!").setMessage("You must select a massage type before continuing.").setNeutralButton("Close", null).show();
		} else if ((massageStyle.getSelectedItemPosition() == 0) && (massageStyle.getVisibility() == View.VISIBLE)) {
			new AlertDialog.Builder(this).setTitle("Attention!").setMessage("You must select a session length before continuing.").setNeutralButton("Close", null).show();
		} else {
		
		String clientAddress = address.getText().toString();
		if (clientAddressLen == 0) {
			clientAddress = "N/A";
		}
		String clientPhone = phone.getText().toString();
		if (clientPhoneLen == 0) {
			clientPhone = "N/A";
		}
		String clientMobilePhone = mobilePhone.getText().toString();
		if (clientMobilePhoneLen == 0) {
			clientMobilePhone = "N/A";
		}
		String clientEmail = email.getText().toString();
		if (clientEmailLen == 0) {
			clientEmail = "N/A";
		}
		
		if (concernLen == 0) {
			clientConcern = "relaxation";
		}
		
		if (notesLen == 0) {
			notesStr = "No notes for this session.";
		}
		
		if (massageStyle.getVisibility() == View.GONE) {
			style = "chair massage";
		}
		
		if (contactInfo.isChecked()) {
			
			dbHelper.insertMassageInfo(clientName, sDate, clientConcern, length, type, style, notesStr);
			
			Toast.makeText(this,"Successfully entered " + clientName + "'s" + " information in the database.", Toast.LENGTH_SHORT).show();
			
			// CRUD Operations
			
			List<ClientEntries> entries = dbHelper.getAllEntries();

			for (ClientEntries ce : entries) {
				String log = "Id: " + ce.getID() + " Name: " + ce.getName() + " Address: " + ce.getAddress() + " Phone: " + ce.getPhone() + " Mobile Phone: " +ce.getMobilePhone() + " Email: " + ce.getEmail();
				
				// Writing entries to log
				Log.d("Entry: ", log);

			}
			
			String massageDate = sDate;
			
			ClientEntries checkDuplicates = new ClientEntries(clientName, notesStr, massageDate);
			
			if (!entries.contains(checkDuplicates)) {
				
				// Inserting entries to DB
				Log.d("Insert: ", "Inserting ..");
				Toast.makeText(this,"Successfully entered " + clientName + "'s" + " information in the database.", Toast.LENGTH_SHORT).show();
				dbHelper.insertClientInfo(clientName, clientAddress, clientPhone, clientMobilePhone, clientEmail);
				String logEntry = "Name: " + clientName + " Address: " + clientAddress + " Phone: " + clientPhone + " Mobile Phone: " + clientMobilePhone + " Email: " + clientEmail;
				Log.d("Entered to DB:", logEntry);
				
				
				
			} else  {
				
				// If client has already been entered in the database, show alert.
				clientAlert();
				
			}
			
			
			
		} else {
			
			dbHelper.insertMassageInfo(clientName, sDate, clientConcern, length, type, style, notesStr);
			Toast.makeText(this,"Successfully entered " + clientName + "'s" + " information in the database.", Toast.LENGTH_SHORT).show();
		}
		
		name.setText("");
		concern.setText("");
		sessionLength.setSelection(0);
		massageType.setSelection(0);
		massageStyle.setSelection(0);
		address.setText("");
		phone.setText("");
		mobilePhone.setText("");
		email.setText("");
		notes.setText("");
		
	}
	}
	private void clientAlert() {
		
		new AlertDialog.Builder(this)
				.setTitle("Attention!")
				.setMessage(
						"This client's information has already been entered in the database. Please select OK below to return to the screen and keep the information in the database, or select Overwrite to enter the information provided today.")
				.setNeutralButton("OK", null)
				.setPositiveButton("Overwrite",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								
								int clientAddressLen = address.length();
								int clientPhoneLen = phone.length();
								int clientMobilePhoneLen = mobilePhone.length();
								int clientEmailLen = email.length();
								
								
								String clientName = name.getText().toString();
								
								String clientAddress = address.getText().toString();
								if (clientAddressLen == 0) {
									clientAddress = "N/A";
								}
								String clientPhone = phone.getText().toString();
								if (clientPhoneLen == 0) {
									clientPhone = "N/A";
								}
								String clientMobilePhone = mobilePhone.getText().toString();
								if (clientMobilePhoneLen == 0) {
									clientMobilePhone = "N/A";
								}
								String clientEmail = email.getText().toString();
								if (clientEmailLen == 0) {
									clientEmail = "N/A";
								}

								dbHelper.deleteClient(clientName);
								Log.d("Overwrite: ", "Inserting ..");
								dbHelper.insertClientInfo(clientName, clientAddress, clientPhone, clientMobilePhone, clientEmail);
								String logEntry = "Name: " + clientName + " Address: " + clientAddress + " Phone: " + clientPhone + " Mobile Phone: " + clientMobilePhone + " Email: " + clientEmail;
								Toast.makeText(CheckIn.this, "Successfuly changed the client's information in the database.", Toast.LENGTH_SHORT).show();  
								Log.d("Entered to DB:", logEntry);
								dialog.dismiss(); // end the dialog.

							}
						})

				.show();
	}
}