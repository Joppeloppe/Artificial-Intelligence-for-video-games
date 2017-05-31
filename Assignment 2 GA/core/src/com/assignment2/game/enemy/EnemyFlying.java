package com.assignment2.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class EnemyFlying
extends Enemy{
/*
 * Flying enemies can only be targeted by certain towers.
 * Medium speed, but weak.
 */
	public EnemyFlying(Texture texture, Vector2 position) {
		super(texture, position);
		setTag("EnemyFlying");
		
		setSpeed(1f);
		setHealthPoint(25);
	}

}
