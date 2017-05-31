package com.assignment3.game.desktop;

import com.assignment3.game.Main;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.height = Main.WINDOW_HEIGHT;
		config.width = Main.WINDOW_WIDTH;
		config.title = "Assignment 3";
		
		new LwjglApplication(new Main(), config);
	}
}
