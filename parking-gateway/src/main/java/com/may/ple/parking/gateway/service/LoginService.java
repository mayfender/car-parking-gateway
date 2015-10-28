package com.may.ple.parking.gateway.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.may.ple.parking.gateway.utils.constant.SettingKey;

public class LoginService<I, O, P> extends AsyncTask <I, O, P> {
	private Context context;
	private RestfulCallback<P> callback;
	private String uri;
	private HttpMethod method;
	private Map<String, String> headersParam;
	
	public LoginService(Context context, RestfulCallback<P> callback, String uri, HttpMethod method, Map<String, String> headers) {
		this.context = context;	
		this.callback = callback;
		this.uri = uri;
		this.method = method;
		this.headersParam = headers;
	}

	@Override
	protected P doInBackground(I... params) {
		try {
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
			
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<?> entity = new HttpEntity<Object>(headers);			
			ResponseEntity<String> resp = restTemplate.exchange(url, method, entity, String.class);
            
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
