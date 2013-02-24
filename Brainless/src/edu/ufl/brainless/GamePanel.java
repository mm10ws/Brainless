package edu.ufl.brainless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final String TAG = GameThread.class.getSimpleName();
	
	private GameThread thread;
	
	public GamePanel(Context context) {
		super(context);
		
		// adding callback(this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		thread = new GameThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	public GamePanel(Context context, AttributeSet attrs) {	
		super(context, attrs);
		
		// adding callback(this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		thread = new GameThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();		
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} 
			else if (event.getY() < 50) {
				Intent in = new Intent((Activity)getContext(), PauseActivity.class);
				Log.d(TAG, "Starting PauseActivity.");
				thread.setRunning(false);
				((Activity)getContext()).startActivity(in);				
			}
			else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		}
		
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
	}
	public void pauseGame() {
		Intent in = new Intent((Activity)getContext(), PauseActivity.class);
		Log.d(TAG, "Starting PauseActivity.");
		thread.setRunning(false);
		((Activity)getContext()).startActivity(in);	
	}
	public void setRunning() {
		thread.setRunning(true);
	}
}
