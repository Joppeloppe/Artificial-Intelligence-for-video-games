package com.assignment2.game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	
	protected String tag;

	protected Texture texture;
	protected Vector2 position, origin;
	protected Rectangle rectangle;
	protected boolean dead;
/*
 * Non-moving object.
 * Has Texture, Position, Origin and a Rectangle for collision and drawing.
 * Boolean dead is used for removal.
 */
	public GameObject(Texture texture, Vector2 position){
		setTexture(texture);
		setPosition(position);
		
		setRectangle(new Rectangle(getPosition().x, getPosition().y,
				getTexture().getWidth(), getTexture().getHeight()));
		
		setOrigin();
		
		setDead(false);
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(getTexture(), getRectangle().x, getRectangle().y);
	}
	
	

/*
 * Checks if current object collides with another GameObject.
 * 
 * @param GameObject to check collision with.
 * @return true if collision, false if not.
 */
	public boolean collision(GameObject other){
		if(getRectangle().overlaps(other.getRectangle())){
			return true;
		}
		
		return false;
	}
/*
 * Getters and setters.	
 */
	public String getTag() {
		return tag;
	}
	
	protected void setTag(String tag) {
		this.tag = tag;
	}
	
	protected Texture getTexture() {
		return texture;
	}
	
	protected void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	protected void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public Vector2 getOrigin() {
		return origin;
	}
	
	protected void setOrigin() {
		origin = new Vector2(getPosition().x + (getTexture().getWidth() / 2),
				getPosition().y + (getTexture().getHeight() / 2));
	}
	
	protected Rectangle getRectangle() {
		return rectangle;
	}
	
	protected void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
