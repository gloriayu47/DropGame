package com.gloria.drop;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gloria.drop.Drop;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//  conserve battery and disable the accelerometer and compass
		config.useAccelerometer=false;
		config.useCompass=false;
		initialize(new Drop(), config);
	}
}
