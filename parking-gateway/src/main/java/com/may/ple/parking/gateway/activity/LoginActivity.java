package com.may.ple.parking.gateway.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.Menu;
import com.may.ple.parking.gateway.criteria.LoginCriteriaResp;
import com.may.ple.parking.gateway.service.CenterService;
import com.may.ple.parking.gateway.service.RestfulCallback;
import com.may.ple.parking.gateway.setting.PreferenceActivitySetting;

public class LoginActivity extends SherlockActivity implements OnClickListener, RestfulCallback {
	private Integer selectedGate;
	
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
    	
    	if(selectedGate == null) {
    		Toast.makeText(this, "กรุณาเลือก Gate", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	String username = ((EditText)findViewById(R.id.user_name)).getText().toString();
    	String password = ((EditText)findViewById(R.id.password)).getText().toString();
    	
    	if(username == null || username.trim().isEmpty()) {
    		Toast.makeText(this, "กรุณากรอก ชื่อผู้ใช้", Toast.LENGTH_SHORT).show();    		
    		return;
    	}
    	if(password == null || password.trim().isEmpty()) {
    		Toast.makeText(this, "กรุณากรอก รหัสผ่าน", Toast.LENGTH_SHORT).show();
    		return;    		
    	}
    	
    	new CenterService(this, this).login(username, password, "/user");
	}
    
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        
        switch(view.getId()) {
            case R.id.checkIn:
                if (checked) selectedGate = 1;
                break;
            case R.id.checkOut:
                if (checked) selectedGate = 2;
                break;
        }
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


	@Override
	public void onComplete(int id, Object result) {
		LoginCriteriaResp resp = (LoginCriteriaResp)result;
		
		if(resp == null) {
			Toast.makeText(this, "ระบบทำงานผิดพลาด กรุณาลองอีกครั้ง", Toast.LENGTH_SHORT).show();
			return;
		}
		else if(resp.statusCode != 9999 || !resp.authenticated) {
			if(resp.statusCode == 5000) {
				Toast.makeText(this, "ไม่สามารถเชื่อมต่อกับข้อมูลกลางได้", Toast.LENGTH_SHORT).show();
			}
			else if(resp.statusCode == 401) {
				Toast.makeText(this, "ข้อมูลไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();				
			}else{
				Toast.makeText(this, "Login ไม่สำเร็จ", Toast.LENGTH_SHORT).show();
			}
			
			return;
		}
		
    	Intent intent = null;
    	if(selectedGate == 1) {
    		intent = new Intent(this, GateInActivity.class);
    	} else if(selectedGate == 2) {
    		intent = new Intent(this, GateOutActivity.class);    		
    	}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
    	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}


}

