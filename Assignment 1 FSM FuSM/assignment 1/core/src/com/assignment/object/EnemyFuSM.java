package com.assignment.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class EnemyFuSM
extends Enemy{
	protected enum State{
		Idle,
		Attack,
		Defend
	}

	protected State currentState;
	private boolean justShot;

	public EnemyFuSM(Texture texture, Vector2 position, Texture projectileTexture) {
		super(texture, position, projectileTexture);
		setSpeed(5f);
		setDetectionRange(500);
		setAttackRange(300);
		setProjectileTexture(projectileTexture);
		
		this.currentState = State.Idle;
		setJustShot(false);
		setTag("FuSM");

	}

	@Override
	public void update(){
		super.update();
		setGunLocation(getOrigin());
		
		updateCurrentState();
	}

	private void updateCurrentState() {		
		setCurrentState();
		
		switch(currentState){
		case Idle:
			updateIdle();
			break;
		case Attack:
			updateAttack();
			break;
		case Defend:
			updateDefend();
			break;
		default:
			break;
		
		}
	}

	
	private void updateDefend() {
		Vector2 newDirection = new Vector2(getDefendPosition().sub(getPosition())).nor();
		float activation = (getDistanceDefend() / getDetectionRange());

		setDirection(new Vector2(getDirection().add(newDirection)));
		setSpeed(5f * activation);
	}

	private void updateAttack() {
		Vector2 projectileDirection = new Vector2(getTargetPosition().sub(getPosition()));
		float activation = (getDistanceTarget() / getAttackRange());

		if(!isJustShot()){
			shoot(projectileDirection, "ProjectileEnemy");
			setJustShot(true);
			
			Timer.schedule(new Task(){
				@Override
				public void run(){
					setJustShot(false);
				}
			}, 0.5f * activation);
		}
	}

	private void updateIdle() {
		setDirection(new Vector2(0, 0));
	}
	

	/*
 * Getters and setters.
 */
	public State getCurrentState() {
		return currentState;
	}

	private void setCurrentState() {
		if(getDefendPosition() != null && objectInRange(getDetectionRange(), getDefendPosition(), "Defend") && getCurrentState() != State.Defend){
			this.currentState = State.Defend;
		}else if(objectInRange(getAttackRange(), getTargetPosition(), "Attack") && getCurrentState() != State.Attack){
			this.currentState = State.Attack;
		}else{
			this.currentState = State.Idle;
		}
	}

	public boolean isJustShot() {
		return justShot;
	}

	public void setJustShot(boolean justShot) {
		this.justShot = justShot;
	}

}
