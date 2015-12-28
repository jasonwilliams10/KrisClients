package com.jwapps.krisclients;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ViewMassages extends ListActivity {
	
	private DatabaseHandler dbHelper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.massage_list);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		dbHelper = new DatabaseHandler(this);
		dbHelper.open();
		displayEvents();
		registerForContextMenu(getListView());
		
	}
    
    @SuppressWarnings("deprecation")
	private void displayEvents() {
    	
    	Bundle extras = getIntent().getExtras();  
		final String clientName = extras.getString("name");

		Cursor massageCursor = dbHelper.getAllMassages(clientName);
		startManagingCursor(massageCursor);
		
		// Create an array to specify the fields we want to display in the list
		// (only TITLE)
		String[] from = new String[] { DatabaseHandler.KEY_DATE };

		// and an array of the fields we want to bind those fields to (in this
		// case just text1)
		int[] to = new int[] { R.id.text1 };

		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter massage = new SimpleCursorAdapter(this,
				R.layout.clients_row, massageCursor, from, to);
		setListAdapter(massage);

	}
    
    @Override
	protected void onListItemClick(ListView l, final View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		final String date = ((TextView) v).getText().toString();
	    Log.i("Date Selected: ", date);
	    
	    Bundle extras = getIntent().getExtras();  
		final String clientName = extras.getString("name");
	    
	    List<ClientEntries> massageEntries = dbHelper.getMassageInfo(clientName, date);
	    //Check to make sure final works with passing info
		for (final ClientEntries CE : massageEntries) {
			String massageLog = "Name: " + CE.getName() + " Area: " +  CE.getArea() + " Type: " + CE.getType() + " Style: " + CE.getStyle() + " Length: " + CE.getLength() + " Notes: " + CE.getNotes();
			
			// Writing entries to log
			Log.i("Massage Info: ", massageLog);

			new AlertDialog.Builder(this).setTitle(CE.getName() + " " + date)
			.setMessage("Area(s) focued on: " + CE.getArea() + "\n"  
					+ "Session length: " + CE.getLength() + "\n"
					+ "Type of massage: " + CE.getType() + "\n"
					+ "Style of massage: " + CE.getStyle() + "\n"
					+ "Notes from this session: " + CE.getNotes())
					
			.setNeutralButton("Close", null)
			.setPositiveButton("Edit",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									
									Intent i = new Intent(ViewMassages.this, EditEntry.class);
									i.putExtra("name", CE.getName());
									i.putExtra("date", date);
									i.putExtra("area", CE.getArea());
									i.putExtra("length", CE.getLength());
									i.putExtra("type" ,CE.getType());
									i.putExtra("style",CE.getStyle());
									i.putExtra("notes",CE.getNotes());
									
									startActivity(i);

								}
							})

					.show();

		}

	}

}
