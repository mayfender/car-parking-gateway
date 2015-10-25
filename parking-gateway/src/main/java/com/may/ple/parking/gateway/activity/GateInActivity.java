package com.may.ple.parking.gateway.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

public class GateInActivity extends SherlockActivity implements OnLongClickListener {
	private String result = "";
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
			if(result.length() > 0)
				result = result.substring(0, result.length() - 1);
		}else{
			if(result.length() >= 4) return;
			
			result += ((Button)view).getText();			
		}
		
		show.setText(result);
	}

	@Override
	public boolean onLongClick(View v) {
		
		if(v.getId() == R.id.show) {
			Toast.makeText(this, "ส่งข้อมูลแล้ว", Toast.LENGTH_SHORT).show();
		}
		
		result = "";
		show.setText(result);			
		
		return false;
	}

}
