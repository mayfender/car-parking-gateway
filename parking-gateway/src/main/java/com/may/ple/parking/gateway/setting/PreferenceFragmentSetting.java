package com.may.ple.parking.gateway.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.may.ple.parking.gateway.R;
import com.may.ple.parking.gateway.utils.constant.SettingKey;

public class PreferenceFragmentSetting extends PreferenceFragment implements OnPreferenceChangeListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_setting);
		EditTextPreference webserviceUrl = (EditTextPreference)findPreference(SettingKey.webserviceUrl);
		EditTextPreference websocketUrl = (EditTextPreference)findPreference(SettingKey.websocketUrl);
		EditTextPreference likeOrderRow = (EditTextPreference)findPreference(SettingKey.likeOrderNum);
		EditTextPreference likeOrderLastDayNum = (EditTextPreference)findPreference(SettingKey.likeOrderLastDayNum);
		
		SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getActivity());
		webserviceUrl.setSummary(setting.getString(SettingKey.webserviceUrl, null));
		websocketUrl.setSummary(setting.getString(SettingKey.websocketUrl, null));
		likeOrderRow.setSummary(setting.getString(SettingKey.likeOrderNum, null)+" รายการ");
		likeOrderLastDayNum.setSummary(setting.getString(SettingKey.likeOrderLastDayNum, null)+" วัน");
		
		webserviceUrl.setOnPreferenceChangeListener(this);
		websocketUrl.setOnPreferenceChangeListener(this);
		likeOrderRow.setOnPreferenceChangeListener(this);
		likeOrderLastDayNum.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if(SettingKey.likeOrderNum.equals(preference.getKey())){			
			preference.setSummary(newValue.toString()+" รายการ");			
		}else if(SettingKey.likeOrderLastDayNum.equals(preference.getKey())) {			
			preference.setSummary(newValue.toString()+" วัน");			
		}else{
			preference.setSummary(newValue.toString());			
		}
		return true;
	}

}
