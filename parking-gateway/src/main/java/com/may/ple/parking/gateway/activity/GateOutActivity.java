package com.may.ple.parking.gateway.activity;

import org.springframework.http.HttpMethod;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.may.ple.parking.gateway.criteria.VehicleCheckOutCriteriaReq;
import com.may.ple.parking.gateway.criteria.VehicleCheckOutCriteriaResp;
import com.may.ple.parking.gateway.dialog.ProgressDialogSpinner;
import com.may.ple.parking.gateway.service.CenterService;
import com.may.ple.parking.gateway.service.RestfulCallback;
import com.may.ple.parking.gateway.utils.constant.SettingKey;

public class GateOutActivity extends SherlockActivity implements RestfulCallback {
	private int reasonNoScan;
	private CenterService service;
	private ProgressDialogSpinner spinner;
	private String gateName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gate_out);
		View type = findViewById(R.id.type);
		registerForContextMenu(type);
		service = new CenterService(this, this);
		spinner = new ProgressDialogSpinner(this);
		
		SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(this);
        gateName = setting.getString(SettingKey.gateName, null);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("เหตุผลที่เลือกใช้การพิมพ์");
		menu.add(0, 1, 0, "Ticket หาย");
		menu.add(0, 2, 1, "Ticket ไม่สามารถสแกนได้");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {	
		reasonNoScan = item.getItemId();
		Intent intent = new Intent(this, GateInActivity.class);
		intent.putExtra("isCheckOut", true);
		startActivityForResult(intent, 1);
		return true;
	}

	public void onClick(View v) {
		if(v.getId() == R.id.scan) {
			Intent intent = new Intent(this, BarcodeScanner.class);			
			startActivity(intent);
		} else if(v.getId() == R.id.type) {
			openContextMenu(v);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String licenseNo = data.getStringExtra("result");                
                
                VehicleCheckOutCriteriaReq req = new VehicleCheckOutCriteriaReq();
                req.licenseNo = licenseNo;
                req.reasonNoScan = reasonNoScan;
                req.deviceId = ApplicationScope.getInstance().deviceId;
                req.gateName = gateName;
                
    			service.passedParam = req;
    			service.send(1, req, VehicleCheckOutCriteriaResp.class, "/restAct/vehicle/checkOutVehicle", HttpMethod.POST);
    			spinner.show();
            }
        } 
    }

	@Override
	public void onComplete(int id, Object obj, Object passedParam) {
		try {
			VehicleCheckOutCriteriaResp resp = (VehicleCheckOutCriteriaResp)obj;
			if(resp == null) {
				Toast.makeText(this, "ระบบทำงานผิดพลาด กรุณาลองอีกครั้ง", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(resp.statusCode != 9999) {
				if(resp.statusCode == 1000)
					Toast.makeText(this, "ระบบทำงานผิดพลาด", Toast.LENGTH_SHORT).show();
				if(resp.statusCode == 3000)
					Toast.makeText(this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
				
				return;
			}
			
			VehicleCheckOutCriteriaReq req = (VehicleCheckOutCriteriaReq)passedParam;
			LayoutInflater inflater = getLayoutInflater();
			
			new AlertDialog.Builder(this)
            .setTitle(getResources().getString(R.string.app_name))
            .setCancelable(false)
            .setView(inflater.inflate(R.layout.alert_dialog, null))
            .setMessage(req.licenseNo + ", " + reasonNoScan + ", id: " + String.format("%011d", resp.vehicleParking.id))
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	
                }
            })
            .show();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			spinner.dismiss();
		}
	}

}
