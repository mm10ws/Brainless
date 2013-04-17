package edu.ufl.brainless;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import java.lang.Override;

public class SplashScreen extends Activity{
	private Thread mSplashThread;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		final SplashScreen sPlashScreen = this;
		mSplashThread = new Thread(){
			@Override
			public void run(){
				try{
					synchronized(this){
						wait(5000);
					}
				}
				catch (InterruptedException ex){
					
				}
				finish();
				Intent intent = new Intent();
				intent.setClass(sPlashScreen, GameActivity.class);
				startActivity(intent);
				stop();
			}
		};
		mSplashThread.start();
	}
	 @Override
	    public boolean onTouchEvent(MotionEvent evt)
	    {
	        if(evt.getAction() == MotionEvent.ACTION_DOWN)
	        {
	            synchronized(mSplashThread){
	                mSplashThread.notifyAll();
	            }
	        }
	        return true;
	    } 
	
	

}
