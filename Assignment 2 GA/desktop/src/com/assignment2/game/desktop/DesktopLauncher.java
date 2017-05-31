package com.assignment2.game.desktop;

import com.assignment2.game.Main;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Assignment 2";
		config.width = Main.WINDOW_WIDHT;
		config.height = Main.WINDOW_HEIGHT;
				
		new LwjglApplication(new Main(), config);
	}
}
