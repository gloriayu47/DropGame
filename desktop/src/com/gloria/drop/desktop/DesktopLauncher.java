package com.gloria.drop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gloria.drop.Drop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//  set the title to "Drop"
		//  800x480 window
		config.title="Drop";
		config.width=800;
		config.height=480;
		new LwjglApplication(new Drop(), config);
	}
}
