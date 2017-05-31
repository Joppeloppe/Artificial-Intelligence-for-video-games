package com.assignment2.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class EnemyBrute
extends Enemy{
/*
 * Slow, but strong enemy.
 */
	public EnemyBrute(Texture texture, Vector2 position) {
		super(texture, position);
		setTag("EnemyBrute");
		
		setSpeed(0.5f);
		setHealthPoint(75);
	}

}
