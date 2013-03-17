package edu.ufl.brainless;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

/** Heads-Up Display
 * Used to display in-game stick and button controls
*/
public class HUD {
	
	private static final String TAG = HUD.class.getSimpleName();
	
	private MotionEvent event;	// Contains input
	private GamePanel panel;	// Used to call getHeight, getWidth, etc.
	
	private Sprite stick;
	private Sprite stickBackground;
	private Sprite button;
	
	private float stickCenterX = 100;
	private float stickCenterY = 375;
	private float buttonCenterX = 600;
	private float buttonCenterY = 375;
	
	// Stick locations
	private float tiltRadius;		// Range where player is tilting stick.
	private float moveRadius;		// Range where player is moving stick.
	private Vector2 stickAngle = new Vector2(0,0);		// Angle of stick input.
	
	private float buttonRadius;
	
	private int stickPointerId = -1;
	private boolean[] buttonPointers = new boolean[10];

	public HUD() {
		stick = new Sprite(ResourceManager.getBitmap(R.drawable.stick_foreground), 45, 355, 0);
		stickBackground = new Sprite(ResourceManager.getBitmap(R.drawable.stick_background), 10, 320, 0);
		stick.setCenter(new Vector2(stickCenterX,stickCenterY));
		stickBackground.setCenter(new Vector2(stickCenterX, stickCenterY));
		tiltRadius = stickBackground.rect.width/6;
		moveRadius = stickBackground.rect.width/2;
		Log.d(TAG, "Stick position: " + stick.position.toString());
		
		button = new Sprite(ResourceManager.getBitmap(R.drawable.stick_background), 10, 320, 0);
		button.setCenter(new Vector2(buttonCenterX,buttonCenterY));
		buttonRadius = button.rect.width;
	}
	
	public void passEvent(MotionEvent event) {
		this.event = event;
		int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int pointerId = event.getPointerId(pointerIndex);
        
        switch (action) {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_POINTER_DOWN:
        	isPointerStickInput(event, pointerId);
        	buttonPointers[pointerId] = isPointerButtonInput(event, pointerId);    
        	Log.d(TAG, "ACTION_DOWN");
            break;
        case MotionEvent.ACTION_UP:        
        case MotionEvent.ACTION_POINTER_UP:
        	if(event.findPointerIndex(stickPointerId) == pointerId)
        		stickPointerId = -1;
        	buttonPointers[pointerId] = false;
        	Log.d(TAG, "ACTION_UP");
        	break;
        case MotionEvent.ACTION_CANCEL:
            buttonPointers[pointerId] = false;
            stickPointerId = -1;
            Log.d(TAG, "ACTION_CANCEL");
            break;
        case MotionEvent.ACTION_MOVE:
        	isPointerStickInput(event, pointerId);
        	buttonPointers[pointerId] = isPointerButtonInput(event, pointerId);
        	
        	if(stickPointerId >= 0) {
        		
        	}
            break;
        }
	}
	
	private boolean isPointerStickInput(MotionEvent event, int pointerId) {
		Vector2 eventVector = new Vector2(event.getX(pointerId), event.getY(pointerId));
		boolean result = false;
		if(Vector2.Distance(stick.getCenter(), eventVector) <= moveRadius) {
			result = true;
			stickPointerId = pointerId;
		}
		return result;
	}
	
	private boolean isPointerButtonInput(MotionEvent event, int pointerId) {
		Vector2 eventVector = new Vector2(event.getX(pointerId), event.getY(pointerId));
		boolean result = false;
		if(Vector2.Distance(button.getCenter(), eventVector) <= buttonRadius) {
			result = true;
		}
		return result;
	}
	
	public boolean isStickPressed() {
		return stickPointerId > -1;
	}
	
	public boolean isButtonPressed() {
		boolean result = false;
		for(int i = 0; i < buttonPointers.length; i++) {
			if(buttonPointers[i])
				result = true;
		}
		return result;
	}

	public void resetHUD() {
		stick.setCenter(stickBackground.getCenter());
	}
	
	public Vector2 getPlayerDirection() {
		if (stick.getCenter().X != stickBackground.getCenter().X || stick.getCenter().Y != stickBackground.getCenter().Y) {
			stickAngle = new Vector2(stick.getCenter().X - stickBackground.getCenter().X, stick.getCenter().Y - stickBackground.getCenter().Y);
			stickAngle.Normalize();
		}
		return stickAngle;
	}
	
	public void update() {
		if(event != null && isStickPressed())  {
			int stickPointerIndex = event.findPointerIndex(stickPointerId);
			float stickX = event.getX(stickPointerIndex);
			float stickY = event.getY(stickPointerIndex);
			stick.setCenter(new Vector2(stickX, stickY));
		}
		else
			resetHUD();
	}
	// test
	public void draw(Canvas canvas) {
		stickBackground.draw(canvas);
		stick.draw(canvas);
		button.draw(canvas);
	}
}