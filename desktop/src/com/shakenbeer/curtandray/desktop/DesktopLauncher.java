package com.shakenbeer.curtandray.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.shakenbeer.curtandray.CurtAndRay;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Curt and Ray";
		config.width = 520;
		config.height = 800;
		new LwjglApplication(new CurtAndRay(), config);
	}
}
