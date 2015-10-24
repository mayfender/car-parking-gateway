package com.may.ple.parking.gateway.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.Menu;
import com.may.ple.parking.gateway.setting.PreferenceActivitySetting;

public class LoginActivity extends SherlockActivity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        findViewById(R.id.login_button).setOnClickListener(this);
    }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.settings_action_provider, menu);
        return true;
    }
    
    
    @Override
	public void onClick(View v) {
    	String userName = ((EditText)findViewById(R.id.user_name)).getText().toString();
    	String password = ((EditText)findViewById(R.id.password)).getText().toString();
    	
    	Intent intent = new Intent(this, GateSelectionActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
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
                }
            });
            return view;
        }
    }

}

