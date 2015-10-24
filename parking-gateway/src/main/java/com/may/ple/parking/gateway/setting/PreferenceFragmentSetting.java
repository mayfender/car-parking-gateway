package com.may.ple.parking.gateway.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.may.ple.parking.gateway.activity.R;
import com.may.ple.parking.gateway.utils.constant.SettingKey;

public class PreferenceFragmentSetting extends PreferenceFragment implements OnPreferenceChangeListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_setting);
		EditTextPreference webserviceUrl = (EditTextPreference)findPreference(SettingKey.webserviceUrl);
		
		SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(getActivity());
		webserviceUrl.setSummary(setting.getString(SettingKey.webserviceUrl, null));
		
		webserviceUrl.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		preference.setSummary(newValue.toString());			
		return true;
	}

}
