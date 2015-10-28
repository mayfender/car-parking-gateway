package com.may.ple.parking.gateway.service;

import java.util.Collections;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.may.ple.parking.gateway.criteria.LoginCriteriaResp;
import com.may.ple.parking.gateway.utils.constant.SettingKey;

public class CenterService {
	private Context context;
	private String uri;
	private HttpMethod method;
	private RestTemplate restTemplate;
	private String url;
	private RestfulCallback callback;
	
	public CenterService(Context context, String uri, HttpMethod method, RestfulCallback callback) {
		this.context = context;	
		this.uri = uri;
		this.method = method;
		this.callback = callback;
		init();
	}
	
	private void init() {
		SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
		String ip = setting.getString(SettingKey.parkingCenterIp, "");
		String port = setting.getString(SettingKey.parkingCenterPort, "");
		url = "http://" + ip + ":" + port + "/parking-center" + uri;
		restTemplate = new RestTemplate();
	}
	
	public void login(String username, String password) {
		new AsyncTask<String, Void, LoginCriteriaResp>() {

			@Override
			protected LoginCriteriaResp doInBackground(String... params) {
				try {
					HttpAuthentication authHeader = new HttpBasicAuthentication(params[0], params[1]);
					HttpHeaders requestHeaders = new HttpHeaders();
					requestHeaders.setAuthorization(authHeader);
					requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
					HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
					
					ResponseEntity<LoginCriteriaResp> respEntity = restTemplate.exchange(url, method, requestEntity, LoginCriteriaResp.class);
					return respEntity.getBody();
				} catch (ResourceAccessException r) {					
					return new LoginCriteriaResp(5000);
				} catch (HttpClientErrorException h) {
					return new LoginCriteriaResp(h.getStatusCode().value());
				} catch (Exception e) {
					return new LoginCriteriaResp(1000);
				} 
			}
			
			protected void onPostExecute(LoginCriteriaResp result) {
				callback.onComplete(result);
			};
			
		}.execute(username, password);
	}
	
}
