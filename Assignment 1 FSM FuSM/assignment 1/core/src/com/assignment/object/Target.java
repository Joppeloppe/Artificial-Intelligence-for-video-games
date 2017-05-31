package com.assignment.object;

import com.assignment.game.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Target
extends MovingObject{

	public Target(Texture texture, Vector2 position) {
		super(texture, position);
		
		setDirection(new Vector2(Main.random.nextFloat(), Main.random.nextFloat()));

		setTag("Target");
		setSpeed(1f);
				
		Timer.schedule(new Task(){
			@Override
			public void run(){
				
				Timer.schedule(new Task(){
					@Override
					public void run(){
						setDead(true);
					}
				}, 10);
			}
		}, 3);
	}
	
	@Override
	public void update(){
		if(outOfBounds()){
			if(getOrigin().x <= 0 || getOrigin().x >= Main.WINDOW_WIDTH){
				setDirection(new Vector2(getDirection().x * -1, getDirection().y));
			}
			
			if(getOrigin().y <= 0 || getOrigin().y >= Main.WINDOW_HEIGHT){
				setDirection(new Vector2(getDirection().x, getDirection().y * -1));
			}
		}
						
		super.update();
	}
}
