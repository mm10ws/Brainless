package edu.ufl.brainless;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PauseActivity extends Activity {
	
	private static final String TAG = GameThread.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pause);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Resume
		Button b1 = (Button) findViewById(R.id.button1);
		
		b1.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Log.d(TAG, "Selected Resume.");
		    	Intent returnIntent = new Intent();
		    	returnIntent.putExtra("choice", "resume");
		    	setResult(Activity.RESULT_OK, returnIntent);
		        returnGame(v);
		    }
		});
		
		// Restart
		Button b2 = (Button) findViewById(R.id.button2);
		
		b2.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Log.d(TAG, "Selected Resume.");
		    	Intent returnIntent = new Intent();
		    	returnIntent.putExtra("choice", "restart");
		    	setResult(Activity.RESULT_OK, returnIntent);
		        returnGame(v);
		    }
		});
		
		// Quit
		Button b3 = (Button) findViewById(R.id.button3);
				
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Selected Resume.");
				Intent returnIntent = new Intent();
				returnIntent.putExtra("choice", "quit");
				setResult(Activity.RESULT_OK, returnIntent);
				returnGame(v);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pause, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void returnGame(View v) {
		Log.d(TAG, "Exiting PauseActivity.");
		finish();
	}

}
