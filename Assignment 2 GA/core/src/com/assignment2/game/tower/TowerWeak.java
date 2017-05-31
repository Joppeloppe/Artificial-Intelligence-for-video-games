package com.assignment2.game.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TowerWeak
extends Tower{

	public TowerWeak(Texture texture, Vector2 position) {
		super(texture, position);
		setTag("TowerWeak");
		
		setRange(150);
		setRateOfFire(0.5f);
		setDamage(1f);
	}

}
