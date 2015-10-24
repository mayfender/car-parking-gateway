package com.may.ple.parking.gateway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.Menu;
import com.may.ple.parking.gateway.setting.PreferenceActivitySetting;

public class LoginActivity extends SherlockActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.settings_action_provider, menu);
        return true;
    }
    
    
    
    
    
    
    public static class SettingsActionProvider extends ActionProvider {
        private final Context mContext;

        public SettingsActionProvider(Context context) {
            super(context);
            mContext = context;
        }
        
        @Override
        public View onCreateActionView() {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.settings_action_provider, null);
            ImageButton button = (ImageButton) view.findViewById(R.id.button);
            
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	Intent i = new Intent(mContext, PreferenceActivitySetting.class);
                	mContext.startActivity(i);
                	((LoginActivity)mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            });
            return view;
        }
    }

}

