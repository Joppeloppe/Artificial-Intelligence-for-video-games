package com.assignment2.game.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TowerStrong
extends Tower{

	public TowerStrong(Texture texture, Vector2 position) {
		super(texture, position);
		setTag("TowerStrong");
		
		setRange(100);
		setRateOfFire(2.0f);
		setDamage(15);
	}

}
