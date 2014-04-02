package com.example.hackathaon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Submission_Page extends Activity implements OnClickListener {
	EditText et;
	Button submit;
	int check, connflag;
	TextView tv;
	String line;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submission_page);
		check = getIntent().getIntExtra("check", -1);
		// Toast.makeText(this, "" + check, Toast.LENGTH_LONG).show();
		et = (EditText) findViewById(R.id.etlciti);
		if (check == 1)
			et.setHint("ENTER PARTY ID");
		tv = (TextView) findViewById(R.id.tvsubnot);
		tv.setOnClickListener(this);
		submit = (Button) findViewById(R.id.bamsubmit);
		submit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// IMPLEMENT NETWORK CHECK
		switch (v.getId()) {
		case R.id.bamsubmit:
			if (et.getText().toString().isEmpty())
				Toast.makeText(this, "ENTER YOUR NUMBER", Toast.LENGTH_LONG)
						.show();
			else {
				if (check == 1)
					startActivity(new Intent(this, Party_Page.class));
				else
					startActivity(new Intent(this, Chooser_Page.class)
							.putExtra("uid", et.getText().toString()));
			}
			break;
		case R.id.tvsubnot:
			startActivity(new Intent(this, User_Reg.class));
		}
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
