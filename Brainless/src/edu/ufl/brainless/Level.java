package edu.ufl.brainless;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.content.res.Resources;
import android.graphics.Canvas;

public class Level extends HUD {
	private Player player; // Player

	private ArrayList<Actor> actors;
	private static final String TAG = Level.class.getSimpleName();
	private Vector2 direction = new Vector2(1,0);
	private Enemy enemy=new Enemy(ResourceManager.getBitmap(R.drawable.enemy),100f,200f,0f,direction,3f,100,false);
	public int bites = 0;
	private static Paint textPaint;
	private static final int WAITING_FOR_SURFACE = 0;
	private static final int COUNTDOWN = 1;
	private static final int RUNNING = 2;
	private static final int GAMEOVER = 3;
	private int mode = WAITING_FOR_SURFACE;

	public Level() {
		player = new Player(ResourceManager.getBitmap(R.drawable.player), 400f, 100f, 0f, 3f, 100, false, new Weapon("Pistol", 8, 3, 1000, 25));
		actors= new ArrayList<Actor>(1);
		actors.add(player);
		actors.add(enemy);
	}
	
	//this method will allow the player to move and stop but the player cannot 'turn' as the stick is moved.
	public void update(HUD hud) {
		player.update(hud);
		enemy.update(player.position);
		collisionCheck();
		if(player.isDead())
			restart();
		for (int i = 0; i < player.heldWeapon.bullet.size(); i++) {
			if ((player.heldWeapon.bullet.get(i).position.X < 0)
					|| (player.heldWeapon.bullet.get(i).position.Y < 0)
					|| (player.heldWeapon.bullet.get(i).position.X > 800 - player.heldWeapon.bullet
							.get(i).rect.width)
					|| (player.heldWeapon.bullet.get(i).position.Y > 480 - player.heldWeapon.bullet
							.get(i).rect.height)) {

				player.heldWeapon.bullet.remove(i);
			} else {
				player.heldWeapon.bullet.get(i).update();
			}
		}
	}
	/*
	 //this will allow the player to move and 'turn' but the player will not stop moving.
	public void update(Vector2 playerDirection) {
		
		player.update(playerDirection);
		enemy.update(player.position);
		collisionCheck();
		if(player.isDead())
			restart();
		for (int i = 0; i < player.heldWeapon.bullet.size(); i++) {
			if ((player.heldWeapon.bullet.get(i).position.X < 0)
					|| (player.heldWeapon.bullet.get(i).position.Y < 0)
					|| (player.heldWeapon.bullet.get(i).position.X > 800 - player.heldWeapon.bullet
							.get(i).rect.width)
					|| (player.heldWeapon.bullet.get(i).position.Y > 480 - player.heldWeapon.bullet
							.get(i).rect.height)) {

				player.heldWeapon.bullet.remove(i);
			} else {
				player.heldWeapon.bullet.get(i).update();
			}
		}

	}
	*/
	public void collisionCheck(){
		Log.d(TAG,"checking for collision");
		//Log.d(TAG,"rectangle height/width "+player.rect.height+" "+player.rect.width);
		Rectangle x=player.rect;
		boolean collision=x.Intersects(x, enemy.rect );

		if (collision){
			bites++;
			player.collision(enemy);
			Log.d(TAG,"collision detected!");
			//decrement health
			healthBar.clear();
			//healthBar = new Sprite(ResourceManager.getBitmap(R.drawable.health_bar1), 585, 15,0);
			//healthBar.draw(null);
			//if health == 0 then set player.isDead = true to restart the game.
		}
	}
	// restart the game
	public void restart(){
		Log.d(TAG,"restart" + bites);
		 enemy=new Enemy(ResourceManager.getBitmap(R.drawable.enemy),100f,200f,0f,direction,3f,100,false);
		player = new Player(ResourceManager.getBitmap(R.drawable.player), 400f, 100f, 0f, 5f, 100, false, new Weapon("Pistol", 8, 3, 1000, 25));
		healthBar = new Sprite(ResourceManager.getBitmap(R.drawable.health_bar + bites), 585, 15,0);
		if (bites ==6){
			mode = GAMEOVER;
		}
	}
	//draw the game over screen.
	public static void drawGameOverScreen(Canvas canvas, float screenHeight, float screenWidth) {
		textPaint = new Paint();
		textPaint.setColor(Color.LTGRAY);
		textPaint.setTextSize(100);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setTextSize(100);
		canvas.drawText("GAME OVER", screenWidth / 2, (float) (screenHeight * 0.50), textPaint);
		textPaint.setTextSize(25);
		//canvas.drawText("You reached level " + level, screenWidth / 2, (float) (screenHeight * 0.60), textPaint);
		//canvas.drawText("Press 'Back' for Main Menu", screenWidth / 2, (float) (screenHeight * 0.85), textPaint);
	}

	public void draw(Canvas canvas) {
		player.draw(canvas);
		enemy.draw(canvas);
		healthBar.draw(canvas);
		for (int i = 0; i < player.heldWeapon.bullet.size(); i++) {
			player.heldWeapon.bullet.get(i).draw(canvas);
		}
		if (mode == GAMEOVER){
			Level.drawGameOverScreen(canvas, 500f, 900f);
			//mode = WAITING_FOR_SURFACE;
			bites = 0;
		}
	}
}