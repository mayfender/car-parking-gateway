package com.may.ple.parking.gateway.activity;

import org.springframework.web.client.RestTemplate;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.may.ple.parking.gateway.criteria.VehicleSaveCriteriaReq;
import com.may.ple.parking.gateway.criteria.VehicleSaveCriteriaResp;
import com.may.ple.parking.gateway.utils.constant.SettingKey;

public class GateInActivity extends SherlockActivity implements OnLongClickListener {
	private String licenseNo = "";
	private TextView show;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gate_in);
        
        show = (TextView)findViewById(R.id.show);
        show.setOnLongClickListener(this);
        
        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnLongClickListener(this);
    }
	
	public void onClick(View view) {
		
		if(view.getId() == R.id.delete) {
			if(licenseNo.length() > 0)
				licenseNo = licenseNo.substring(0, licenseNo.length() - 1);
		}else{
			if(licenseNo.length() >= 4) return;
			
			licenseNo += ((Button)view).getText();			
		}
		
		show.setText(licenseNo);
	}

	@Override
	public boolean onLongClick(View v) {
		
		if(v.getId() == R.id.show) {
			if(licenseNo == null || licenseNo.trim().length() == 0) return false;
						
			new SendData().execute(licenseNo);
		}
	
		licenseNo = "";
		show.setText(licenseNo);			
		return false;
	}
	
	
	class SendData extends AsyncTask <String, Void, VehicleSaveCriteriaResp> {

		@Override
		protected VehicleSaveCriteriaResp doInBackground(String... params) {
			try {
				SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(GateInActivity.this);
				String ip = setting.getString(SettingKey.parkingCenterIp, "");
				String port = setting.getString(SettingKey.parkingCenterPort, "");
				
				String url = "http://" + ip + ":" + port + "/parking-center/restAct/vehicle/saveVehicleParking";
				
				VehicleSaveCriteriaReq req = new VehicleSaveCriteriaReq();
				req.setLicenseNo(params[0]);
				
				RestTemplate restTemplate = new RestTemplate();
				return restTemplate.postForObject(url, req, VehicleSaveCriteriaResp.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override	
		protected void onPostExecute(VehicleSaveCriteriaResp resp) {
			if(resp != null)
				Toast.makeText(GateInActivity.this, "ส่งข้อมูลแล้ว status: " +resp.getStatusCode(), Toast.LENGTH_SHORT).show();
		}
		
	}

}
