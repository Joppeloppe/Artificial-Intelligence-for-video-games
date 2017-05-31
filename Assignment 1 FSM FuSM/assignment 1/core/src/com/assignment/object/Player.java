package com.assignment.object;

import com.assignment.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player 
extends ShootingObject{

	public Player(Texture texture, Vector2 position, Texture projectileTexture) {
		super(texture, position, projectileTexture);		
		setTag("Player");
		setSpeed(4f);
	}
	
	@Override
	public void update(){
		super.update();
		setGunLocation(getOrigin());
		setWithinBorder();
		
		updateInput();	
	}

	private void updateInput() {
		if(Gdx.input.justTouched()){
			Vector3 vector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			Main.getCamera().unproject(vector);
			
			Vector2 projDirection = new Vector2(vector.x, vector.y).sub(getPosition()).nor();
			
			shoot(projDirection, "ProjectilePlayer");
		}
		
		setSpeed(4f);
		
		if(Gdx.input.isKeyPressed(Keys.W)){
			getDirection().y = 1 * getSpeed();
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			getDirection().y = -1 * getSpeed();
		}
		
		if(Gdx.input.isKeyPressed(Keys.D)){
			getDirection().x = 1 * getSpeed();
		}
		else if(Gdx.input.isKeyPressed(Keys.A)){
			getDirection().x = -1 * getSpeed();
		}
		
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			setSpeed(getSpeed() * 2);
		}
		
		if(!Gdx.input.isKeyPressed(Keys.ANY_KEY)){
			getDirection().setZero();
		}
	}
	
	public void collisionUpdate(Enemy enemy){
		if(collides(enemy)){
			setDead(true);
		}
	}

	private void setWithinBorder() {
		if(getPosition().x <= 0){
			getPosition().x = 0;
			getDirection().x = 0;
		}else if(getPosition().x >= Main.WINDOW_WIDTH - getTexture().getWidth()){
			getPosition().x = Main.WINDOW_WIDTH - getTexture().getWidth();
			getDirection().x = 0;
		}
		
		if(getPosition().y <= 0){
			getPosition().y = 0;
			getDirection().y = 0;
		}else if(getPosition().y >= Main.WINDOW_HEIGHT - getTexture().getHeight()){
			getPosition().y = Main.WINDOW_HEIGHT - getTexture().getHeight();
			getDirection().y = 0;
		}
	}
}
