package com.may.ple.parking.gateway.activity;

import android.app.Application;

public class ApplicationScope extends Application {
	private static ApplicationScope instance = null;
	public String jsessionid;
	public int connTimeout = 10000;
	public int readTimeout = 10000;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static ApplicationScope getInstance() {
		return instance;
	}

}
