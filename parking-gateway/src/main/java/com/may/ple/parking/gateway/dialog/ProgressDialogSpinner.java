package com.may.ple.parking.gateway.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.may.ple.parking.gateway.activity.R;

public class ProgressDialogSpinner {
	private Dialog dialog;
	
	public ProgressDialogSpinner(Context context) {		
		try {
			dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.progressdialog);
			dialog.setCancelable(false);
		} catch (Exception e) {
			
		}		
	}
	
	public void show() {
		dialog.show();
	}
	
	public void dismiss() {
		dialog.dismiss();
	}

}
