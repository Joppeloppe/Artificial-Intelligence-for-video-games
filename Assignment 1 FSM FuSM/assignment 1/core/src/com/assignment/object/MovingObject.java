package com.assignment.object;

import com.assignment.game.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class MovingObject
extends GameObject{

	protected float speed;
	protected boolean dead;
	
	protected Vector2 direction = new Vector2(0, 0);

	public MovingObject(Texture texture, Vector2 position) {
		super(texture, position);
		
		setDead(false);
	}
	
	@Override
	public void update(){		
		setDirection(new Vector2(getDirection().nor()));
		
		setPosition(new Vector2(getPosition().add(new Vector2(getDirection().x * speed,
				getDirection().y * speed))));
		
		super.update();
	}
	
	public boolean outOfBounds(){
		if(getPosition().x <= 0 || getPosition().x >= Main.WINDOW_WIDTH - getTexture().getWidth() ||
				getPosition().y <= 0 || getPosition().y >= Main.WINDOW_HEIGHT - getTexture().getHeight()){
			return true;
		}
		return false;
	}
	
	public boolean collides(GameObject object){
		if(getRectangle().overlaps(object.getRectangle())){
			return true;
		}
		
		return false;
	}
	
/*
 * Getters and setters
 */
	public float getSpeed() {
		return speed;
	}
	
	protected void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public Vector2 getDirection() {
		return direction;
	}
	
	protected void setDirection(Vector2 direction) {
		this.direction = direction;
	}

}
