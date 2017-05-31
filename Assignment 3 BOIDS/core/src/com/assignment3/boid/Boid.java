package com.assignment3.boid;

import java.util.List;

import com.assignment3.game.Main;
import com.assignment3.gameObject.GameObjectMoving;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Boid
extends GameObjectMoving{
	public final float MASS = 20.0f, RANGE = 150.0f;
	private boolean changeTarget;
	
	private Vector2 steering, desiredDirection, targetPosition;

	public Boid(Texture texture, Vector2 position) {
		super(texture, position);		
		setSpeed(2.0f);
		setDirection(new Vector2(-1, -1).nor());
		setChangeTarget(true);
	}
	
	
	@Override
	public void update(){
		seekTarget();
		
		super.update();
		setSpeed(2.0f);
	}
/*
 * Steers boid to target.
 */
	private void seekTarget(){
		setDesiredDirection(new Vector2(new Vector2(getTargetPosition()).sub(getPosition())));
		
		setSteering(new Vector2(getDesiredDirection().sub(getDirection()).nor()));
		setSteering(new Vector2(getSteering().x / MASS, 
				getSteering().y / MASS));
		
		setDirection(new Vector2(getDirection().add(getSteering())));
	}
/*
 * Pursuits the target.
 */
	public void pursuit(GameObjectMoving target){
		float dt = 5f;
		
		float targetSpeed = target.getSpeed();
		Vector2 targetDirection = new Vector2(target.getDirection());
		Vector2 futureTarget = new Vector2(target.getPosition());
		
		float x = futureTarget.x + (targetDirection.x * targetSpeed * dt);
		float y = futureTarget.y + (targetDirection.y * targetSpeed * dt);

//Use .add instead of .set, to easier see the pursuit.
		futureTarget.set(new Vector2(x, y));
		//futureTarget.add(new Vector2(x, y));

		
		setTargetPosition(futureTarget);
	}
		
/*
 * Makes the boid wander around the screen by randomising the position of the target.
 */
	public void wander(){
		if(getChangeTarget()){
			int x = Main.random.nextInt(1080);
			int y = Main.random.nextInt(720);
			
			Vector2 newTarget = new Vector2(x, y);
			setTargetPosition(newTarget);
			
			setChangeTarget(false);
			Timer.schedule(new Task(){
				@Override
				public void run(){
					setChangeTarget(true);
				}
			}, 1.5f);
		}
	}
/*
 * Computes the alignment of the current boid with those in range.
 */
	public Vector2 alignment(List<Boid> listBoid){
		Vector2 alignment = new Vector2(0, 0);
		int neighborCount = 0;
		
		for(int i = 0; i < listBoid.size(); i++){
			Boid agent = listBoid.get(i);
			
			if(agent != this){
				Vector2 otherPosition = new Vector2(agent.getPosition());
				float distance = new Vector2(getPosition()).dst(otherPosition);
				
				if(distance < RANGE){
					alignment.set(new Vector2(agent.getDirection()));
					neighborCount++;
				}
			}
		}
		if(neighborCount == 0){
			return alignment;
		}
		
		alignment.set(alignment.x / neighborCount,
				alignment.y / neighborCount);
		
		alignment.nor();
		
		return alignment;
	}
/*
 * Computes the cohesion of the current boid with those in range.
 */
	public Vector2 cohesion(List<Boid> listBoid){
		Vector2 cohesion = new Vector2(0, 0);
		int neighborCount = 0;
		
		for(int i = 0; i < listBoid.size(); i++){
			Boid agent = listBoid.get(i);
			
			if(agent != this){
				Vector2 otherPosition = new Vector2(agent.getPosition());
				float distance = new Vector2(getPosition()).dst(otherPosition);
				
				if(distance < RANGE){
					cohesion.add(new Vector2(otherPosition));
					neighborCount++;					
				}
			}
		}
		
		if(neighborCount == 0)
			return cohesion;
		
		cohesion.set(new Vector2(cohesion.x / neighborCount,
				cohesion.y / neighborCount));
		
		cohesion.set(new Vector2(cohesion.x - new Vector2(getPosition()).x,
				cohesion.y - new Vector2(getPosition()).y));
		
		cohesion.nor();
		
		return cohesion;
	}
/*
 * Computes the separation of the current boid with those in range.
 */
	public Vector2 separation(List<Boid> listBoid){
		Vector2 separation = new Vector2(0, 0);
		int neighbourCount = 0;
		
		for(int i = 0; i < listBoid.size(); i++){
			Boid agent = listBoid.get(i);
			
			if(agent != this){
				Vector2 otherPosition = new Vector2(agent.getPosition());
				float distance = new Vector2(getPosition()).dst(otherPosition);
				
				if(distance < RANGE){
					separation.add(new Vector2(otherPosition.sub(new Vector2(getPosition()))));
					
					neighbourCount++;
				}
			}
		}
		
		if(neighbourCount == 0)
			return separation;
		
		separation.set(new Vector2((separation.x / neighbourCount), 
				(separation.y / neighbourCount)));
		
		separation.set(new Vector2(separation.x * -1, 
				separation.y * -1));
		
		separation.nor();
		
		return separation;
	}
/*
 * Boids slows down when it gets closer to the target.	
 */
	public void arrive(){
		float distance = getTargetPosition().dst(getPosition());
		
		if(distance < RANGE){
			float activation = distance / (RANGE * 2);
			
			setSpeed(getSpeed() * activation);
		}else
			setSpeed(2.0f);
	}
/*
 * Getters and setters.
 */
	private Vector2 getSteering() {
		return steering;
	}

	private void setSteering(Vector2 steering) {
		this.steering = steering;
	}

	private Vector2 getDesiredDirection() {
		return desiredDirection;
	}

	private void setDesiredDirection(Vector2 desiredDirection) {
		this.desiredDirection = desiredDirection;
	}

	private Vector2 getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(Vector2 targetPosition) {
		this.targetPosition = targetPosition;
	}

	public boolean getChangeTarget() {
		return changeTarget;
	}

	private void setChangeTarget(boolean changeTarget) {
		this.changeTarget = changeTarget;
	}
}
