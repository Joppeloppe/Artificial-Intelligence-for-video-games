package com.assignment3.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetsManager {
	public Texture boidsTexture, shipTexture;
	public BitmapFont font;
	
	public void loadAssets(){
		boidsTexture = new Texture(Gdx.files.internal("boid.png"));
		shipTexture = new Texture(Gdx.files.internal("ship.png"));
		
		font = new BitmapFont();
	}
	
	public void dispose(){
		boidsTexture.dispose();
		shipTexture.dispose();
		
		font.dispose();
	}
}
