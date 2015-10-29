package com.may.ple.parking.gateway.activity;

import android.app.Application;

public class ApplicationScope extends Application {
	private static ApplicationScope instance = null;
	public String jsessionid;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static ApplicationScope getInstance() {
		return instance;
	}

}
