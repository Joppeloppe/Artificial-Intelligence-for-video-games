package com.assignment.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.assignment.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Assignment 1";
		config.height = Main.WINDOW_HEIGHT;
		config.width = Main.WINDOW_WIDTH;
		
		new LwjglApplication(new Main(), config);
	}
}
