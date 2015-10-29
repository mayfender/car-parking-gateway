package com.may.ple.parking.gateway.activity;

import org.springframework.http.HttpMethod;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.may.ple.parking.gateway.criteria.VehicleSaveCriteriaReq;
import com.may.ple.parking.gateway.criteria.VehicleSaveCriteriaResp;
import com.may.ple.parking.gateway.service.CenterService;
import com.may.ple.parking.gateway.service.RestfulCallback;

public class GateInActivity extends SherlockActivity implements OnLongClickListener, RestfulCallback {
	private String licenseNo = "";
	private TextView show;
	private CenterService service;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gate_in);
        
        show = (TextView)findViewById(R.id.show);
        show.setOnLongClickListener(this);
        
        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnLongClickListener(this);
        service = new CenterService(this, this);
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
						
			VehicleSaveCriteriaReq req = new VehicleSaveCriteriaReq();
			req.setLicenseNo(licenseNo);
			service.send(1, req, VehicleSaveCriteriaResp.class, "/restAct/vehicle/saveVehicleParking", HttpMethod.POST);
		}
	
		licenseNo = "";
		show.setText(licenseNo);			
		return false;
	}

	@Override
	public void onComplete(int id, Object obj) {
		Toast.makeText(this, "Sent already", Toast.LENGTH_SHORT).show();
	}
	
}
