package com.assignment.object;

import com.assignment.game.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ShootingObject 
extends MovingObject{

	protected Texture projectileTexture;
	protected Vector2 gunLocation, targetLocation;
	
	public ShootingObject(Texture texture, Vector2 position, Texture projectileTexture) {
		super(texture, position);
		setProjectileTexture(projectileTexture);
		setGunLocation(getOrigin());
	}

	protected void shoot(Vector2 projDirection, String projectileTag){	
		Main.addProjectile(new Projectile(projectileTexture,
				new Vector2(getGunLocation()),
				projDirection, projectileTag));
	}

	
	protected boolean targetInRange(float range){
		float distance = getTargetLocation().dst(getOrigin());
		
		if(distance < range){
			return true;
		}
		
		return false;
	}
	
	
/*
 * Getters and setters.
 */
	public Texture getProjectileTexture() {
		return projectileTexture;
	}

	public void setProjectileTexture(Texture projectileTexture) {
		this.projectileTexture = projectileTexture;
	}

	public Vector2 getGunLocation() {
		return gunLocation;
	}

	public void setGunLocation(Vector2 gunPosition) {
		this.gunLocation = gunPosition;
	}

	public Vector2 getTargetLocation() {
		return targetLocation;
	}

	public void setTargetLocation(Vector2 targetPosition) {
		this.targetLocation = new Vector2(targetPosition);
	}

}
