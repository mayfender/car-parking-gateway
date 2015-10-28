package com.may.ple.parking.gateway.service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.may.ple.parking.gateway.utils.constant.SettingKey;

public class Service {
	private Context context;
	private String uri;
	private Map<String, String> headers;
	
	public Service(Context context, Map<String, String> headersParam, String uri) {
		this.uri = uri;
		this.headers = headersParam;
		
		SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
		String ip = setting.getString(SettingKey.parkingCenterIp, "");
		String port = setting.getString(SettingKey.parkingCenterPort, "");
		String url = "http://" + ip + ":" + port + "/parking-center" + uri;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(new MediaType("application","json")));
		
		if(headersParam != null) {
			Set<String> keys = headersParam.keySet();
			for (String key : keys) {
				headers.add(key, headersParam.get(key));
			}
		}
		
	}
	
	public void login() {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> entity = new HttpEntity<Object>(headers);		
		ResponseEntity<String> resp = restTemplate.exchange(url, method, entity, String.class);
	}

}
