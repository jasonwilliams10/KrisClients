package com.jwapps.krisclients;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewClients extends ListActivity {
	
	private DatabaseHandler dbHelper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.clients_list);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		dbHelper = new DatabaseHandler(this);
		dbHelper.open();
		displayEvents();
		registerForContextMenu(getListView());
	}
    
    @SuppressWarnings("deprecation")
	private void displayEvents() {

		Cursor notesCursor = dbHelper.getAllClients();
		startManagingCursor(notesCursor);

		// Create an array to specify the fields we want to display in the list
		// (only TITLE)
		String[] from = new String[] { DatabaseHandler.KEY_CLIENT_NAME };

		// and an array of the fields we want to bind those fields to (in this
		// case just text1)
		int[] to = new int[] { R.id.text1 };

		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.clients_row, notesCursor, from, to);
		setListAdapter(notes);

	}
    
    @Override
	protected void onListItemClick(ListView l, final View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		final String name = ((TextView) v).getText().toString();

		Log.i("onListClicked name: ", name);
		
		Intent i = new Intent(ViewClients.this, ClientInfo.class);
		i.putExtra("name", name);
		startActivity(i);
		
	}
}