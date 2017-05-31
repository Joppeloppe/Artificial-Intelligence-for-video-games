package com.assignment2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetsManager {

	public BitmapFont font;
	public Texture enemyBrute, enemySwarmling, enemyFlying,
	towerWeak, towerStrong,
	road, grass,
	castle00;
	
	public void loadAssets(){
		font = new BitmapFont();
		
		enemyBrute = new Texture(Gdx.files.internal("enemyBrute.png"));
		enemySwarmling = new Texture(Gdx.files.internal("enemySwarmling.png"));
		enemyFlying = new Texture(Gdx.files.internal("enemyFlying.png"));
		
		towerWeak = new Texture(Gdx.files.internal("tower00.png"));
		towerStrong = new Texture(Gdx.files.internal("tower01.png"));
		
		road = new Texture(Gdx.files.internal("road.png"));
		grass = new Texture(Gdx.files.internal("grass00.png"));
		
		castle00 = new Texture(Gdx.files.internal("castle00.png"));
	}
	
	public void dispose(){
		font.dispose();
		
		enemyBrute.dispose();
		enemySwarmling.dispose();
		enemyFlying.dispose();
		
		towerWeak.dispose();
		towerStrong.dispose();
		
		road.dispose();
		grass.dispose();
		
		castle00.dispose();
	}
	
}
