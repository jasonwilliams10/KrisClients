package com.jwapps.krisclients;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class EditEntry extends Activity {

	TextView nameLbl;
	EditText name;
	TextView dateLbl;
	EditText date;
	TextView areaLbl;
	EditText area;
	TextView sessionLbl;
	EditText session;
	TextView typeLbl;
	EditText type;
	TextView styleLbl;
	EditText style;
	TextView notesLbl;
	EditText notes;

	private DatabaseHandler dbHelper;
	String packageName = "com.jwapps.krisclients";
	String db = "clientList";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_entry);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		dbHelper = new DatabaseHandler(this);
		dbHelper.open();

		nameLbl = (TextView) findViewById(R.id.nameLbl);
		name = (EditText) findViewById(R.id.name);
		
		dateLbl = (TextView) findViewById(R.id.dateLbl);
		date = (EditText) findViewById(R.id.date);
		
		areaLbl = (TextView) findViewById(R.id.areaLbl);
		area = (EditText) findViewById(R.id.area);
		
		sessionLbl = (TextView) findViewById(R.id.sessionLbl);
		session = (EditText) findViewById(R.id.session);
		
		typeLbl = (TextView) findViewById(R.id.typeLbl);
		type = (EditText) findViewById(R.id.type);
		
		styleLbl = (TextView) findViewById(R.id.styleLbl);
		style = (EditText) findViewById(R.id.style);
		
		notesLbl = (TextView) findViewById(R.id.notesLbl);
		notes = (EditText) findViewById(R.id.notes);

		
		Bundle extras = getIntent().getExtras();  
		final String clientName = extras.getString("name");
		final String massageDate = extras.getString("date");
		final String massageArea = extras.getString("area");
		final String massageLength = extras.getString("length");
		final String massageType = extras.getString("type");
		final String massageStyle = extras.getString("style");
		final String massageNotes = extras.getString("notes");
		
		name.setText(clientName);
		date.setText(massageDate);
		area.setText(massageArea);
		session.setText(massageLength);
		type.setText(massageType);
		style.setText(massageStyle);
		notes.setText(massageNotes);
		
	}
	
	}