package com.assignment2.game.tower;

import com.assignment2.game.enemy.Enemy;
import com.assignment2.game.object.GameObject;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Tower
extends GameObject{
	
	protected float range, rateOfFire, damage;
	protected boolean hasShot = false;

	public Tower(Texture texture, Vector2 position) {
		super(texture, position);
		setTag("Tower");
	}
/*
 * Deals damage to target enemy, and starts the cooldown.	
 */
	public void shoot(Enemy enemy){
		setHasShot(true);
		
		enemy.takeDamage(getDamage());
		
		Timer.schedule(new Task(){
			@Override
			public void run(){
				setHasShot(false);
			}
		}, getRateOfFire());
	}
	
	public float calculateDistanceToTarget(GameObject target){
		Vector2 targetPos = new Vector2(target.getOrigin());
		float distance = targetPos.dst(getOrigin());
		
		return distance;
	}


	
/*
 * Getters and setters.
 */
	public float getRange() {
		return range;
	}
	
	protected void setRange(float range) {
		this.range = range;
	}

	protected float getRateOfFire() {
		return rateOfFire;
	}

	protected void setRateOfFire(float rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	protected float getDamage() {
		return damage;
	}

	protected void setDamage(float damage) {
		this.damage = damage;
	}

	public boolean isHasShot() {
		return hasShot;
	}

	protected void setHasShot(boolean hasShot) {
		this.hasShot = hasShot;
	}
}
