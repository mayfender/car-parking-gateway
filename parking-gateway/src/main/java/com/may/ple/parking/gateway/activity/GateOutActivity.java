package com.may.ple.parking.gateway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class GateOutActivity extends SherlockActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gate_out);
	}

	public void onClick(View v) {
        Intent intent = new Intent(this, BarcodeScanner.class);
        startActivity(intent);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("result");
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();               
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } 
    }

}
