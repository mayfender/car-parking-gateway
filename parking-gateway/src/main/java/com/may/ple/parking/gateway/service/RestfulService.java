package com.may.ple.parking.gateway.service;

import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.may.ple.parking.gateway.utils.constant.SettingKey;

public class RestfulService<I, O, P> extends AsyncTask <I, O, P> {
	private Context context;
	private RestfulCallback<P> callback;
	
	public RestfulService(Context context, RestfulCallback<P> callback) {
		this.context = context;	
		this.callback = callback;
	}

	@Override
	protected P doInBackground(I... params) {
		SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
		String ip = setting.getString(SettingKey.parkingCenterIp, "");
		String port = setting.getString(SettingKey.parkingCenterPort, "");
		
		String url = "http://" + ip + ":" + port + "/parking-center/restAct/vehicle/saveVehicleParking";
		
		RestTemplate restTemplate = new RestTemplate();
		P result = (P)restTemplate.postForObject(url, params[0], Object.class);
		return result;
	}
	
	@Override
	protected void onPostExecute(P result) {
		try {
			callback.onComplete(result);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
