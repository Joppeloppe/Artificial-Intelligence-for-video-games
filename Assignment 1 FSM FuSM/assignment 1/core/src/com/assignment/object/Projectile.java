package com.assignment.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Projectile
extends MovingObject{

	public Projectile(Texture texture, Vector2 position, Vector2 direction, String tag) {
		super(texture, position);
		setDirection(direction);
		setTag(tag);
		setSpeed(6f);
				
		Timer.schedule(new Task(){
			@Override
			public void run(){
				setDead(true);
			}
		}, 1.75f);
	}
	
	@Override
	public void update(){
		if(outOfBounds()){
			setDead(true);
		}
		
		super.update();
	}
}
