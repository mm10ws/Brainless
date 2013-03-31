package edu.ufl.brainless;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class Level {
	public Player player; // Player
	private ArrayList<Actor> actors;
	private static final String TAG = Level.class.getSimpleName();
	private Vector2 direction= new Vector2(1,0);
	private Enemy enemy=new Enemy(ResourceManager.getBitmap(R.drawable.enemy),100f,200f,0f,direction,0f,100,false);
	public Level() {
		player = new Player(ResourceManager.getBitmap(R.drawable.player), 400f, 100f, 0f, 3f, 100, false, new Weapon("Pistol", 8, 3, 1000, 25));
		actors= new ArrayList<Actor>(1);
		actors.add(player);
		actors.add(enemy);
	}
	
	public void update(Vector2 playerDirection) {
		
		player.update(playerDirection);
		enemy.update();
		collisionCheck();
		if(player.isDead())
			restart();
			
	}
	
	public void collisionCheck(){
		Log.d(TAG,"checking for collision");
		//Log.d(TAG,"rectangle height/width "+player.rect.height+" "+player.rect.width);
		Rectangle x=player.rect;
		boolean collision=x.Intersects(x, enemy.rect );
		
		if (collision){
			player.collision(enemy);
			Log.d(TAG,"collision detected!");
		}
	}
	
	public void restart(){
		 enemy=new Enemy(ResourceManager.getBitmap(R.drawable.enemy),100f,200f,0f,direction,0f,100,false);
		player = new Player(ResourceManager.getBitmap(R.drawable.player), 400f, 100f, 0f, 3f, 100, false, new Weapon("Pistol", 8, 3, 1000, 25));
		
	}
	public void draw(Canvas canvas) {
		player.draw(canvas);
		enemy.draw(canvas);
	}
}
