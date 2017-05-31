package com.assignment.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetsManager {
	public Texture playerTexture,
	enemyTexture,
	enemy1Texture,
	projectileTexture,
	targetTexture,
	powerUpTexture;
	
	public BitmapFont font;
		
	public void loadContent(){
		playerTexture = new Texture(Gdx.files.internal("friend.png"));
		enemyTexture = new Texture(Gdx.files.internal("enemy.png"));
		enemy1Texture = new Texture(Gdx.files.internal("enemy1.png"));
		projectileTexture = new Texture(Gdx.files.internal("projectile.png"));
		targetTexture = new Texture(Gdx.files.internal("target.png"));
		powerUpTexture = new Texture(Gdx.files.internal("powerUp.png"));
		
		font = new BitmapFont();
	}
	
	public void dispose(){
		playerTexture.dispose();
		enemyTexture.dispose();
		enemyTexture.dispose();
		projectileTexture.dispose();
		targetTexture.dispose();
		powerUpTexture.dispose();
		
		font.dispose();
	}
}
