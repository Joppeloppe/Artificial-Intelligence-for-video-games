package com.assignment3.gameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GameObjectMoving
extends GameObject{
	private Vector2 direction;
	private float speed;
	
	public GameObjectMoving(Texture texture, Vector2 position) {
		super(texture, position);
	}
	
	public void update(){
		setDirection(getDirection().nor());
		
		setRotation((float)Math.atan2(getDirection().y, getDirection().x));
		
		setPosition(new Vector2(getPosition().add(getDirection().x * getSpeed(),
				getDirection().y * getSpeed())));
				
		getRectangle().setPosition(new Vector2(getPosition().x, getPosition().y));
		setOrigin();

	}

/*
 * Getters and setters.
 */
	public Vector2 getDirection() {
		return direction;
	}

	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
