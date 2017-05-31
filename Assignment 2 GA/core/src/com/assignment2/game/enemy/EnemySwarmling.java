package com.assignment2.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class EnemySwarmling
extends Enemy{
/*
 * Fast, but weak enemy.
 */
	public EnemySwarmling(Texture texture, Vector2 position) {
		super(texture, position);
		setTag("EnemySwarmling");
		
		setSpeed(1.5f);
		setHealthPoint(25);
	}

}
