package edu.ufl.brainless;

import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final String TAG = GameThread.class.getSimpleName();
	
	private GameThread thread;
	Context context;
	public GamePanel(Context context) {
		super(context);
		this.context=context;
		
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
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			thread.addEventToHud(event);
			Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			//thread.setRunning(false);
			//((Activity)getContext()).finish();
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
			thread.removeEventFromHud();
		
		return super.onTouchEvent(event);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
	}
	
}
