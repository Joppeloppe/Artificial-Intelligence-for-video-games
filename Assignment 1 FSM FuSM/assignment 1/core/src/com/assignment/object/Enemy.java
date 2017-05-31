package com.assignment.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Enemy
extends ShootingObject{
	protected float detectionRange, attackRange, distanceTarget, distanceDefend;

	protected Vector2 targetPosition, defendPosition;


	public Enemy(Texture texture, Vector2 position, Texture projectileTexture) {
		super(texture, position, null);
		setTag("Enemy");
	}
		
	protected boolean objectInRange(float range, Vector2 objectPosition, String type){
		float distance = objectPosition.dst(getPosition());
		
		if(distance < range){
			if(type == "Attack")
				setDistanceTarget(distance);
			else if(type == "Defend")
				setDistanceDefend(distance);
			return true;
		}
		
		return false;
	}
	
	
	
/*
 * Getters and setters.
 */
	protected float getDistanceDefend() {
		return distanceDefend;
	}

	protected void setDistanceDefend(float distanceDefend) {
		this.distanceDefend = distanceDefend;
	}
	
	protected float getDistanceTarget() {
		return distanceTarget;
	}
	
	protected void setDistanceTarget(float distanceTarget) {
		this.distanceTarget = distanceTarget;
	}
	
	public float getDetectionRange() {
		return detectionRange;
	}

	protected void setDetectionRange(float range) {
		this.detectionRange = range;
	}

	public float getAttackRange() {
		return attackRange;
	}

	protected void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
	}
	
	public Vector2 getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(Vector2 targetPosition) {
		this.targetPosition = new Vector2(targetPosition);
	}
	
	public Vector2 getDefendPosition() {
		return defendPosition;
	}

	public void setDefendPosition(Vector2 defendPosition) {
		this.defendPosition = defendPosition;
	}

}