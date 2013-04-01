package edu.ufl.brainless;

import java.util.ArrayList;

public class Weapon extends Item {
	int ammoRemaining;
	final int constAmmoInClip; //keep track of original number of bullets in clip
	int ammoInClip; // changes as the gun is fired
	int numberOfClips;
	int reloadTime;	
	int weaponDamage;
	public ArrayList<Bullet> bullet = new ArrayList<Bullet>();//1
	private static final String TAG = Weapon.class.getSimpleName();
	protected Bullet b1;

	//constructors
	public Weapon(){ // default constructor
		super();
		numberOfClips = 3; //default have 3 clips
		constAmmoInClip = 10; //default ammo in clip is 10
		ammoInClip = constAmmoInClip;
		ammoRemaining = numberOfClips*ammoInClip; 		
		reloadTime = 2;
		weaponDamage = 50;		
	}	

	public Weapon(String weaponName, int constAmmoInClip, int numberOfClips,
			int reloadTime, int weaponDamage){
		super(weaponName);
		this.constAmmoInClip = constAmmoInClip;
		this.numberOfClips = numberOfClips;
		this.ammoInClip = constAmmoInClip;
		this.ammoRemaining = numberOfClips*ammoInClip;		
		this.reloadTime = reloadTime;
		this.weaponDamage = weaponDamage;
	}

	// setters and getters
	public int getAmmoRemaining() {
		return ammoRemaining;
	}

	public void setAmmoRemaining(int ammoRemaining) {
		this.ammoRemaining = ammoRemaining;
	}

	public int getAmmoInClip() {
		return ammoInClip;
	}

	public void setAmmoInClip(int ammoInClip) {
		this.ammoInClip = ammoInClip;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(int reloadTime) {
		this.reloadTime = reloadTime;
	}

	public int getWeaponDamage() {
		return weaponDamage;
	}

	public void setWeaponDamage(int weaponDamage) {
		this.weaponDamage = weaponDamage;
	}

	public int getNumberOfClips() {
		return numberOfClips;
	}

	public void setNumberOfClips(int numberOfClips) {
		this.numberOfClips = numberOfClips;
	}

	//shoot and reload methods
	public boolean shoot(float x, float y, float angle, Vector2 direction, float speed){
		if (this.ammoRemaining == 0){			
			return false;
		}		
		else if(this.ammoRemaining != 0 && this.ammoInClip == 0){
			this.reload();
			return false;
		}
		else{
			SoundManager.playSound(1, 1.0f, false);
			Bullet b1 = new Bullet(ResourceManager.getBitmap(R.drawable.bullet),x, y,angle,direction,speed);//1
			bullet.add(b1);
			//added this line for collision with bullet.
			//((Actor) b1).update();
			this.ammoRemaining --;
			this.ammoInClip --;
			if(this.ammoRemaining == 0){
				this.numberOfClips = 0;
			}
			return true;			
		}
	}

	public void reload(){
		this.numberOfClips --;
		this.ammoInClip = constAmmoInClip;
	}
}
