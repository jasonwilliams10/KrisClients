package com.jwapps.krisclients;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class KrisClientsActivity extends Activity {

	private DatabaseHandler dbHelper;
	String packageName = "com.jwapps.krisclients";
	String db = "clientList";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		dbHelper = new DatabaseHandler(this);
		dbHelper.open();

		Button search = (Button) findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						ViewClients.class);
				startActivityForResult(myIntent, 0);

			}
		});

		Button checkIn = (Button) findViewById(R.id.checkIn);
		checkIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), CheckIn.class);
				startActivityForResult(myIntent, 0);

			}
		});

		Button backup = (Button) findViewById(R.id.backup);
		backup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				InputStream myInput;

				try {

					myInput = new FileInputStream(
							"/data/data/com.jwapps.krisclients/databases/clientList");
					// this is the path for all apps

					// Set the output folder on the SDcard
					File directory = new File("/sdcard/KrisClients");
					// Create the folder if it doesn't exist:
					if (!directory.exists()) {
						directory.mkdirs();
					}
					// Set the output file stream up:

					OutputStream myOutput = new FileOutputStream(directory
							.getPath() + "/clientList.backup");

					// Transfer bytes from the input file to the output file
					byte[] buffer = new byte[1024];
					int length;
					while ((length = myInput.read(buffer)) > 0) {
						myOutput.write(buffer, 0, length);
					}
					// Close and clear the streams

					myOutput.flush();

					myOutput.close();

					myInput.close();

				} catch (FileNotFoundException e) {
					Toast.makeText(KrisClientsActivity.this,
							"Backup Unsuccesfull!", Toast.LENGTH_LONG).show();

					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					Toast.makeText(KrisClientsActivity.this,
							"Backup Unsuccesfull!", Toast.LENGTH_LONG).show();

					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(KrisClientsActivity.this,
						"Backed up Database Succesfully!", Toast.LENGTH_LONG)
						.show();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.importDB:

			new AlertDialog.Builder(this)
					.setTitle("Attention!")
					.setMessage(
							"This will import the database from the SD card into the application. By doing this, it will overwrite anything that is in the database at this time. Do you wish to continue?")
					.setNeutralButton("No", null)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {

									OutputStream myOutput;

									try {

										myOutput = new FileOutputStream(
												"/data/data/com.jwapps.krisclients/databases/clientList");

										// Set the folder on the SDcard
										File directory = new File(
												"/sdcard/KrisClients");
										// Set the input file stream up:

										InputStream myInputs = new FileInputStream(
												directory.getPath()
														+ "/clientList.backup");

										// Transfer bytes from the input file to
										// the output file
										byte[] buffer = new byte[1024];
										int length;
										while ((length = myInputs.read(buffer)) > 0) {
											myOutput.write(buffer, 0, length);
										}

										// Close and clear the streams
										myOutput.flush();

										myOutput.close();

										myInputs.close();

									} catch (FileNotFoundException e) {
										Toast.makeText(
												KrisClientsActivity.this,
												"Import Unsuccesfull!",
												Toast.LENGTH_LONG).show();

										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										Toast.makeText(
												KrisClientsActivity.this,
												"Import Unsuccesfull!",
												Toast.LENGTH_LONG).show();

										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Toast.makeText(KrisClientsActivity.this,
											"Import Done Succesfully!",
											Toast.LENGTH_LONG).show();

								}
							})

					.show();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}