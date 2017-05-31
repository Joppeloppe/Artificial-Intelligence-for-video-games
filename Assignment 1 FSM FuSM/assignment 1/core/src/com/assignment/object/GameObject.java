package com.assignment.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {	
	protected String tag;

	protected Texture texture;
	protected Vector2 position, origin;
	protected Rectangle rectangle;
	
	public GameObject(Texture texture, Vector2 position){
		setTexture(texture);
		setPosition(position);
		
		setRectangle(new Rectangle(position.x, position.y,
				texture.getWidth(), texture.getHeight()));
		
		setOrigin();
	}
	
	public void update(){
		getRectangle().setPosition(new Vector2(getPosition().x, getPosition().y));
		setOrigin();
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(getTexture(), getPosition().x, getPosition().y);
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
	
	public Texture getTexture() {
		return texture;
	}
	
	protected void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public Vector2 getOrigin() {
		return origin;
	}
	
	protected void setOrigin(){
		origin = new Vector2(position.x + (texture.getWidth() / 2),
				position.y + (texture.getHeight() / 2));
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	protected void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
}
