package com.assignment.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class EnemyFSM
extends Enemy{
	public static enum State{
		Idle,
		Chase,
		Attack
	}
	
	protected State currentState;
	
	public EnemyFSM(Texture texture, Vector2 position, Texture projectileTexture) {
		super(texture, position, projectileTexture);
		setSpeed(3f);
		setDetectionRange(500);
		setAttackRange(100);
		setTag("FSM");
		
		setCurrentState(State.Idle);
	}
	
	@Override
	public void update(){
		super.update();
		
		updateCurrentState();
	}
	
	private void updateCurrentState(){
		if(objectInRange(getDetectionRange(), getTargetPosition(), "Attack") && getCurrentState() != State.Chase){
			setCurrentState(State.Chase);
		}else if(!objectInRange(getDetectionRange(), getTargetPosition(), "Attack") && getCurrentState() != State.Idle ||
				!objectInRange(getAttackRange(), getTargetPosition(), "Attack") && getCurrentState() != State.Idle){
			setCurrentState(State.Idle);
		}else if(objectInRange(getAttackRange(), getTargetPosition(), "Attack") && getCurrentState() != State.Attack){
			setCurrentState(State.Attack);
		}
		
		switch(getCurrentState()){
		case Idle:
			updateIdle();
			break;
		case Chase:
			updateChase();
			break;
		case Attack:
			updateAttack();
		default:
				break;
		}
	}

	private void updateChase(){
		Vector2 newDirection = new Vector2(getTargetPosition().sub(getPosition()));

		setDirection(newDirection);
	}
	
	private void updateIdle(){
		setDirection(new Vector2(0, 0));
	}
	
	private void updateAttack(){
		setSpeed(getSpeed() * 2);
	}
	
	
/*
 * Getters and setters.
 */
	public State getCurrentState() {
		return currentState;
	}

	private void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
}
