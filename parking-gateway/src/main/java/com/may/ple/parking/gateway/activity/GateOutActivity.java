package com.may.ple.parking.gateway.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;

public class GateOutActivity extends SherlockActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gate_out);
	}

	public void onClick(View v) {
		Intent intent = null;
		
		if(v.getId() == R.id.scan) {
			intent = new Intent(this, BarcodeScanner.class);			
			startActivity(intent);
		} else if(v.getId() == R.id.type) {
			intent = new Intent(this, GateInActivity.class);
			intent.putExtra("isCheckOut", true);
			startActivityForResult(intent, 1);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");                
                
                new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(false)
                .setMessage(result)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	
                    }
                }).show();
            }
        } 
    }

}
