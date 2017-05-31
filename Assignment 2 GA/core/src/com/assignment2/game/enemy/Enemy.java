package com.assignment2.game.enemy;

import java.util.ArrayList;
import java.util.List;

import com.assignment2.game.object.GameObject;
import com.assignment2.game.object.GameObjectMoving;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Enemy
extends GameObjectMoving{

	private float healthPoint = 0;
	private int currentWaypoint = 0;
	
	private List<Vector2> waypoints = new ArrayList<Vector2>();
	
/*
 * Generic enemy.
 */
	public Enemy(Texture texture, Vector2 position) {
		super(texture, position);
		setTag("Enemy");
	}

	@Override
	public void update(){
		if(getHealthPoint() <= 0)
			setDead(true);
		
		calculateDirection();
		
		super.update();
	}

	public void takeDamage(float damage){
		setHealthPoint(getHealthPoint() - damage);
	}
/*
 * Calculates the direction of the enemy to the next waypoint.
 */
	private void calculateDirection(){
		if(waypoints.size() > 0){
			if(getDistanceToWaypoint() <= getSpeed()){
				setPosition(new Vector2(waypoints.remove(0)));
				setCurrentWaypoint(getCurrentWaypoint() + 1);
			}else{		
				Vector2 target = new Vector2(waypoints.get(0));
				Vector2 newDirection = new Vector2(target.sub(getPosition())); 
				setDirection(newDirection);
			}
		}else{
			setDirection(new Vector2(0, 0));
		}
	}
	
	
	
/*
 * Getters and setters.
 */
	protected float getHealthPoint() {
		return healthPoint;
	}
	
	protected void setHealthPoint(float hitpoint) {
		this.healthPoint = hitpoint;
	}
	
	public void setWaypoints(List<GameObject> paramList){
		for(int i = 0; i < paramList.size(); i++){
			Vector2 newWaypoint = new Vector2(paramList.get(i).getPosition());
			
			waypoints.add(newWaypoint);
		}
	}

	protected float getDistanceToWaypoint() {	
		float distance = waypoints.get(0).dst(getPosition());
		return distance;
	}

	public int getCurrentWaypoint() {
		return currentWaypoint;
	}

	private void setCurrentWaypoint(int currentWaypoint) {
		this.currentWaypoint = currentWaypoint;
	}

}
