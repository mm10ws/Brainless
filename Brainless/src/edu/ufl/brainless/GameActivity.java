package edu.ufl.brainless;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * The GameActivity class is the main activity for Brainless. It gets and loads
 */
public class GameActivity extends Activity {

	private static final String TAG = GameActivity.class.getSimpleName();
	private GamePanel panel;
	private String bString = "Button";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(TAG, "Creating...");
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // make game full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set GamePanel as the View
        //setContentView(new GamePanel(this));
        setContentView(R.layout.activity_game);
        panel = (GamePanel) findViewById(R.id.gamepanel);
        if(panel instanceof GamePanel) {Log.d(TAG, "GamePanel found.");}
        setContentView(R.layout.activity_game);
        Log.d(TAG, "View added");
        Button b = (Button) findViewById(R.id.button1);
    	b.setText(bString);
    }
    
    @Override
    protected void onDestroy() {
    	Log.d(TAG, "Destroying...");
    	super.onDestroy();
    }
    
    @Override
    protected void onPause() {
    	Log.d(TAG, "Pausing...");
    	panel.setRunning(false);
    	super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.d(TAG, "Request Code: " + requestCode);
    	switch (requestCode) {
    		case 1010:
    			if(resultCode != Activity.RESULT_OK) {Log.d(TAG, "resultCode != RESULT_OK.");}
    			if(resultCode == Activity.RESULT_OK) {
    				Log.d(TAG, "resultCode == RESULT_OK. data.getStringExtra(\"choice\") = " + data.getStringExtra("choice"));
    				if(data.getStringExtra("choice").equals("resume")) {
    					Log.d(TAG, "Pause menu called resume.");
    					onRestart();
    				}
    				else if(data.getStringExtra("choice").equals("restart")) {
    					// Call custom function
    					Log.d(TAG, "Pause menu called restart.");
    					bString = "Button.";
    					onRestart();
    					
    				}
    				else if(data.getStringExtra("choice").equals("quit")) {
    					// Call custom function
    					Log.d(TAG, "Pause menu called exit.");
    					panel = (GamePanel) findViewById(R.id.gamepanel);
    			        if(panel instanceof GamePanel) {Log.d(TAG, "GamePanel found.");}
    					panel.endGame();
    				}
    			}
    		default: break;		
    	}
    	
    }
    
    @Override
    protected void onRestart() {
        Log.d(TAG, "Restarting...");
        setContentView(R.layout.activity_game);
        panel = (GamePanel) findViewById(R.id.gamepanel);
        Log.d(TAG, "View added");
        panel.setRunning(true);
        super.onRestart();
        Button b = (Button) findViewById(R.id.button1);
    	b.setText(bString);
    }
    
    public void pauseGame(View v) {
    	panel = (GamePanel) findViewById(R.id.gamepanel);
        if(panel instanceof GamePanel) {Log.d(TAG, "GamePanel found.");}
        panel.setRunning(false);
        panel.pauseGame();	
    }
    
    public void changeText(View v) {
    	Button b = (Button) findViewById(R.id.button1);	
    	if(bString.equals("Changed.")) { bString = "Reverted.";}
    	else { bString = "Changed.";}
    	b.setText(bString);
    	Log.d(TAG,"Text Changed.");
    	panel = (GamePanel) findViewById(R.id.gamepanel);
    	panel.changeTest++;
        if(panel instanceof GamePanel) {Log.d(TAG, "GamePanel found. changeTest = " + panel.changeTest);}
        
    }
    
    
}
