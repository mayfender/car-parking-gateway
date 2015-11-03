package com.may.ple.parking.gateway.activity;

import org.springframework.http.HttpMethod;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.may.ple.parking.gateway.criteria.VehicleGetCriteriaReq;
import com.may.ple.parking.gateway.criteria.VehicleGetCriteriaResp;
import com.may.ple.parking.gateway.dialog.ProgressDialogSpinner;
import com.may.ple.parking.gateway.service.CenterService;
import com.may.ple.parking.gateway.service.RestfulCallback;

public class GateOutActivity extends SherlockActivity implements RestfulCallback {
	private int remark;
	private CenterService service;
	private ProgressDialogSpinner spinner;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gate_out);
		View type = findViewById(R.id.type);
		registerForContextMenu(type);
		service = new CenterService(this, this);
		spinner = new ProgressDialogSpinner(this);
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
		remark = item.getItemId();
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
                
                VehicleGetCriteriaReq req = new VehicleGetCriteriaReq();
                req.licenseNo = licenseNo;
    			service.passedParam = req;
    			service.send(1, req, VehicleGetCriteriaResp.class, "/restAct/vehicle/getVehicleParking", HttpMethod.POST);
    			spinner.show();
            }
        } 
    }

	@Override
	public void onComplete(int id, Object obj, Object passedParam) {
		try {
			
			VehicleGetCriteriaResp resp = (VehicleGetCriteriaResp)obj;
			if(resp == null) {
				Toast.makeText(this, "ระบบทำงานผิดพลาด กรุณาลองอีกครั้ง", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(resp.statusCode != 9999) {
				if(resp.statusCode == 1000) {
					Toast.makeText(this, "ระบบทำงานผิดพลาด", Toast.LENGTH_SHORT).show();
				}
				return;
			}
			
			VehicleGetCriteriaReq req = (VehicleGetCriteriaReq)passedParam;
			
			new AlertDialog.Builder(this)
            .setTitle(getResources().getString(R.string.app_name))
            .setCancelable(false)
            .setMessage(req.licenseNo + ", " + remark + ", id: " + resp.id)
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
