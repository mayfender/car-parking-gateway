package com.may.ple.parking.gateway.activity;

import org.springframework.http.HttpMethod;

import android.content.Intent;
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
import com.may.ple.parking.gateway.dialog.ProgressDialogSpinner;
import com.may.ple.parking.gateway.service.CenterService;
import com.may.ple.parking.gateway.service.RestfulCallback;

public class GateInActivity extends SherlockActivity implements OnLongClickListener, RestfulCallback {
	private String licenseNo = "";
	private TextView show;
	private CenterService service;
	private ProgressDialogSpinner spinner;
	private boolean isCheckOut;
	private int lenght = 4;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCheckOut = getIntent().getBooleanExtra("isCheckOut", false);
        spinner = new ProgressDialogSpinner(this);
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
			if(licenseNo.length() >= lenght) return;
			
			licenseNo += ((Button)view).getText();			
		}
		
		show.setText(licenseNo);
	}

	@Override
	public boolean onLongClick(View v) {
		
		if(v.getId() == R.id.show) {
			if(licenseNo == null || licenseNo.trim().length() == 0) return false;
			
			show.setBackgroundResource(R.drawable.text_show_sent);
			
			if(isCheckOut) {
				Intent returnIntent = new Intent();
                returnIntent.putExtra("result", licenseNo);
				setResult(RESULT_OK, returnIntent);
				finish();
				return true;
			}
			
			VehicleSaveCriteriaReq req = new VehicleSaveCriteriaReq();
			req.setLicenseNo(licenseNo);
			service.send(1, req, VehicleSaveCriteriaResp.class, "/restAct/vehicle/saveVehicleParking", HttpMethod.POST);
			spinner.show();
		} else {
			licenseNo = "";
			show.setText(licenseNo);
		}
		return true;
	}

	@Override
	public void onComplete(int id, Object obj, Object passedParam) {
		try {
			licenseNo = "";
			show.setText(licenseNo);
			show.setBackgroundResource(R.drawable.text_show);
			Toast.makeText(this, "Sent already", Toast.LENGTH_SHORT).show();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			spinner.dismiss();
		}
	}

}
