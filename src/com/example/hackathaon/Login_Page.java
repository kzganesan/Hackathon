package com.example.hackathaon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Login_Page extends Activity implements OnClickListener {
	Button bciti, bparty;
	int connflag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		bciti = (Button) findViewById(R.id.blciti);
		bparty = (Button) findViewById(R.id.blparty);
		bciti.setOnClickListener(this);
		bparty.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// IMPLEMENT NETWORK CHECK
		// networkcheck();
		// if (connflag == 1) {
		switch (v.getId()) {
		case R.id.blciti:
			startActivity(new Intent(this, Submission_Page.class).putExtra(
					"check", 0));
			break;
		case R.id.blparty:
			startActivity(new Intent(this, Submission_Page.class).putExtra(
					"check", 1));

		}
		// } else
		// Toast.makeText(this, "CHECK NETWORK CONNECTION", Toast.LENGTH_LONG)
		// .show();
	}

	public void networkcheck() {
		ConnectivityManager connectivity = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] info = connectivity.getAllNetworkInfo();
		if (info != null)
			for (int i = 0; i < info.length; i++)
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					connflag = 1;
					break;
				}
	}
}
