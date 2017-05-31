package com.assignment2.game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameObjectMoving
extends GameObject{

	protected float speed;
	
	protected Vector2 direction = new Vector2(0, 0);
/*
 * Moving object.
 * Inherits from GameObject.
 * Has speed and direction.
 */
	public GameObjectMoving(Texture texture, Vector2 position) {
		super(texture, position);
	}
	
	public void update(){
		setDirection(getDirection().nor());
	
		setPosition(new Vector2(getPosition().add(new Vector2(getDirection().x * getSpeed(),
				getDirection().y * getSpeed()))));
		
		getRectangle().setPosition(new Vector2(getPosition().x, getPosition().y));
		
		setOrigin();
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(getTexture(), getPosition().x, getPosition().y);
	}
	
/*
 * Getters and setters.
 */
	protected float getSpeed() {
		return speed;
	}

	protected void setSpeed(float speed) {
		this.speed = speed;
	}

	protected Vector2 getDirection() {
		return direction;
	}

	protected void setDirection(Vector2 direction) {
		this.direction = direction;
	}

}
