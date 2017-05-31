package com.assignment3.gameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	private boolean dead;
	private float rotation = 0;
	
	private Texture texture;
	private Vector2 position, origin;
	private Rectangle rectangle;
	private TextureRegion textureRegion;

	public GameObject(Texture texture, Vector2 position){
		setTexture(texture);
		setPosition(position);
		
		setOrigin();
		
		setRectangle(new Rectangle(getPosition().x, getPosition().y,
				texture.getWidth(), texture.getHeight()));
		
		setDead(false);
		
		textureRegion = new TextureRegion(getTexture());
	}

	public void draw(SpriteBatch spriteBatch){
		spriteBatch.draw(textureRegion, getRectangle().x, getRectangle().y,
				getOrigin().x, getOrigin().y, getTexture().getWidth(),
				getTexture().getHeight(), 1, 1, 0);;
	}

/*
 * Collision check with another GameObject.
 * 
 * @param GameObject other, other GameObject to check collision against.
 * @return true if collision occurs.
 */
	public boolean collision(GameObject other){
		return getRectangle().overlaps(other.getRectangle());
	}
	
	
	
/*
 * Getters and setters
 */
	protected Texture getTexture() {
		return texture;
	}

	private void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = new Vector2(position);
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

	protected float getRotation() {
		return rotation;
	}

	protected void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public Vector2 getOrigin() {
		return origin;
	}

	public void setOrigin() {
		this.origin = new Vector2(getPosition().x + (getTexture().getWidth() / 2),
				getPosition().y + (getTexture().getHeight() / 2));
	}
}
