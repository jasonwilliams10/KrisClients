package com.jwapps.krisclients;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ClientInfo extends Activity {
	
	TextView name;
	TextView address;
	TextView phone;
	TextView mobilePhone;
	TextView email;
	TextView massageInfo;
	TextView notesLbl;
	
	 EditText editName;
	 EditText editAddress;
	 EditText editPhone;
	 EditText editMobilePhone;
	 EditText editEmail;
	 
	 TextView notes;
	 
	 Button saveChanges;
	 Button viewAll;
	 
	 private DatabaseHandler dbHelper;
	 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_info);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		dbHelper = new DatabaseHandler(this);
		dbHelper.open();
		
		name = (TextView) findViewById(R.id.name);
		address = (TextView) findViewById(R.id.address);
		phone = (TextView) findViewById(R.id.phone);
		mobilePhone = (TextView) findViewById(R.id.mobilePhone);
		email = (TextView) findViewById(R.id.email);
		massageInfo = (TextView) findViewById(R.id.massageInfo);
		editName = (EditText) findViewById(R.id.editName);
		editAddress = (EditText) findViewById(R.id.editAddress);
		editPhone = (EditText) findViewById(R.id.editPhone);
		editMobilePhone = (EditText) findViewById(R.id.editMobilePhone);
		editEmail = (EditText) findViewById(R.id.editEmail);
		notes = (TextView) findViewById(R.id.notes);
		notesLbl = (TextView) findViewById(R.id.notesLbl);
		saveChanges = (Button) findViewById(R.id.saveChanges);
		viewAll = (Button) findViewById(R.id.viewAll);
		
		
		editName.setVisibility(View.GONE);
		editAddress.setVisibility(View.GONE);
		editPhone.setVisibility(View.GONE);
		editMobilePhone.setVisibility(View.GONE);
		editEmail.setVisibility(View.GONE);
		saveChanges.setVisibility(View.GONE);

		
		
		Bundle extras = getIntent().getExtras();  
		final String clientName = extras.getString("name");
		
		Integer chairCount = dbHelper.getChairCount(clientName);
		
		Integer tableCount = dbHelper.getTableCount(clientName);

		String chairCountStr = Integer.toString(chairCount);
		String tableCountStr = Integer.toString(tableCount);

		Log.i("chair count: ", chairCountStr);
		Log.i("table count: ", tableCountStr);
		
		Cursor dateCursor = dbHelper.getLastDate(clientName);
		
		final String date = dateCursor.getString(0);
		
		Log.i("Last massage date: ", date);
		
		Cursor areaCursor = dbHelper.getLastAreaWorked(date, clientName);
		
		String areaWorked = areaCursor.getString(0);
		
		Log.i("Last area worked: ", areaWorked);
		
		
		editName.setText(clientName);
		
		name.setText(clientName);
		
		notesLbl.setText("Notes from " + date + ":");
		
		viewAll.setText("View all of " + clientName + "'s massages");
		
		address.setText("There is not an address for " + clientName + " in the database.");
		phone.setText("The is not a phone number for " + clientName + " in the database.");
		mobilePhone.setText("There is not a mobile phone number for " + clientName + " in the database.");
		email.setText("There is not an email address for " + clientName + " in the database.");			

		editAddress.setText("There is not an address for " + clientName + " in the database.");
		editPhone.setText("The is not a phone number for " + clientName + " in the database.");
		editMobilePhone.setText("There is not a mobile phone number for " + clientName + " in the database.");
		editEmail.setText("There is not an email address for " + clientName + " in the database.");
		
		List<ClientEntries> massageEntries = dbHelper.getMassageInfo(clientName, date);

		for (ClientEntries CE : massageEntries) {
			String massageLog = "Area: " +  CE.getArea() + " Type: " + CE.getType() + " Style: " + CE.getStyle() + " Length: " + CE.getLength() + " Notes: " + CE.getNotes();
			
			// Writing entries to log
			Log.i("Massage Info: ", massageLog);

			massageInfo.setText(clientName + " has been in the chair " + chairCountStr + " time(s), and has been on the table " + tableCountStr + " time(s)."
					+ " The last time you massaged " + clientName + " was " + date + "." + " This was a " + CE.getType() + " massage, the style was " + CE.getStyle() + ", " +
					"the massage lasted " + CE.getLength() + ", and you focused on " + clientName + "'s " + CE.getArea()+ ".");
			
			notes.setText(CE.getNotes());
			
		}
		
		
		
		List<ClientEntries> entries = dbHelper.getClientInfo(clientName);

		for (ClientEntries ce : entries) {
			String log = "Id: " + ce.getID() + " Name: " + ce.getName() + " Address: " + ce.getAddress() + " Phone: " + ce.getPhone() + " Mobile Phone: " +ce.getMobilePhone() + " Email: " + ce.getEmail();
			
			// Writing entries to log
			Log.d("Entry: ", log);
			
			int addressLen = address.length();
			int phoneLen = phone.length();
			int mobilePhoneLen = mobilePhone.length();
			int emailLen = email.length();
			
			address.setText(ce.getAddress());
			phone.setText(ce.getPhone());
			mobilePhone.setText(ce.getMobilePhone());
			email.setText(ce.getEmail());			
			
			editAddress.setText(ce.getAddress());
			editPhone.setText(ce.getPhone());
			editMobilePhone.setText(ce.getMobilePhone());
			editEmail.setText(ce.getEmail());
			
		}	
		
		// Save changes to client
		saveChanges.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					
					clientAlert();
			
			}
		});
		
		viewAll.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				Intent i = new Intent(ClientInfo.this, ViewMassages.class);
				i.putExtra("name", clientName);
				i.putExtra("date", date);
				startActivity(i);
				
			}
		});
				

	
	email.setOnClickListener(new View.OnClickListener() {
		 @Override
		  public void onClick(View v) {
		
			 Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				String[] recipient = new String[] { email.getText().toString() };
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipient);
				emailIntent.setType("text/plain");			
				startActivity(Intent.createChooser(emailIntent, "Sending Email..."));
			 
			}
		});

		mobilePhone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				new AlertDialog.Builder(ClientInfo.this)
						.setTitle("Attention!")
						.setMessage("Please select to call or text " + clientName + ".")
						.setPositiveButton("Cancel", null)
						.setNegativeButton("Send SMS",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {

										Intent smsIntent = new Intent(
												Intent.ACTION_VIEW);
										smsIntent.putExtra("address",
												mobilePhone.getText()
														.toString());
										smsIntent
												.setType("vnd.android-dir/mms-sms");
										startActivity(smsIntent);

									}
								})

						.setNeutralButton("Call",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {

										Intent callIntent = new Intent(
												Intent.ACTION_CALL);
										callIntent.setData(Uri.parse("tel:"
												+ phone.getText().toString()));
										startActivity(callIntent);

									}
								})

						.show();
			}
		});
	
	phone.setOnClickListener(new View.OnClickListener() {
		 @Override
		  public void onClick(View v) {
		   
			 new AlertDialog.Builder(ClientInfo.this)
				.setTitle("Attention!")
				.setMessage("Please select to call or text " + clientName + ".")
				.setPositiveButton("Cancel", null)
				.setNegativeButton("Send SMS",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								Intent smsIntent = new Intent(
										Intent.ACTION_VIEW);
								smsIntent.putExtra("address",
										mobilePhone.getText()
												.toString());
								smsIntent
										.setType("vnd.android-dir/mms-sms");
								startActivity(smsIntent);

							}
						})

				.setNeutralButton("Call",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								Intent callIntent = new Intent(
										Intent.ACTION_CALL);
								callIntent.setData(Uri.parse("tel:"
										+ phone.getText().toString()));
								startActivity(callIntent);

							}
						})

				.show();
			 
		}
	});
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_menu, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.editClient:

			name.setVisibility(View.GONE);
			address.setVisibility(View.GONE);
			phone.setVisibility(View.GONE);
			mobilePhone.setVisibility(View.GONE);
			email.setVisibility(View.GONE);
			
			
			editName.setVisibility(View.VISIBLE);
			editAddress.setVisibility(View.VISIBLE);
			editPhone.setVisibility(View.VISIBLE);
			editMobilePhone.setVisibility(View.VISIBLE);
			editEmail.setVisibility(View.VISIBLE);
			saveChanges.setVisibility(View.VISIBLE);
			
		return true;
		
		case R.id.home:
		
		Intent i = new Intent(ClientInfo.this, KrisClientsActivity.class);
		startActivity(i);
		
		return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void clientAlert() {
		
		new AlertDialog.Builder(this)
				.setTitle("Attention!")
				.setMessage("You are about to change the client's information, do you wish to continue?")
				.setNeutralButton("No", null)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								
								int clientAddressLen = editAddress.length();
								int clientPhoneLen = editPhone.length();
								int clientMobilePhoneLen = editMobilePhone.length();
								int clientEmailLen = editEmail.length();
								
								String clientName = editName.getText().toString();
								
								String clientAddress = editAddress.getText().toString();
								if (clientAddressLen == 0) {
									clientAddress = "N/A";
								} 
								
								String clientPhone = editPhone.getText().toString();
								if (clientPhoneLen == 0) {
									clientPhone = "N/A";
								}
								String clientMobilePhone = editMobilePhone.getText().toString();
								if (clientMobilePhoneLen == 0) {
									clientMobilePhone = "N/A";
								}
								String clientEmail = editEmail.getText().toString();
								if (clientEmailLen == 0) {
									clientEmail = "N/A";
								}
							
							dbHelper.deleteClient(clientName);
							dbHelper.insertClientInfo(clientName, clientAddress, clientPhone, clientMobilePhone, clientEmail);
							
							Toast.makeText(ClientInfo.this,"Successfully updated the client's information in the database.", Toast.LENGTH_SHORT).show();

							}
						})

				.show();
	}
}