package com.may.ple.parking.gateway.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;

public class GateOutActivity extends SherlockActivity {
	private int remark;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gate_out);
		View type = findViewById(R.id.type);
		registerForContextMenu(type);
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
                String result = data.getStringExtra("result");                
                
                new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(false)
                .setMessage(result + ", " + remark)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	
                    }
                }).show();
            }
        } 
    }

}
