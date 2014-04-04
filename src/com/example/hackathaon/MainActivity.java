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

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Fragment implements OnItemSelectedListener,
		OnClickListener {
	String[] imp = { "LEVEL1", "LEVEL2", "LEVEL3" };
	String[] tl1 = { "ELECTRICITY", "WATER", "TRANSPORT", "INFRASTRUCTURE" };
	String[] tl2 = { "AGRICULTURE", "SOCIAL WELFARE", "ENVIRONMENT", "HEALTH",
			"EDUCATION", "RURAL DEVELOPMENT", "TOURISM", "CULTURE" };
	String[] tl3 = { "RAILWAY", "REVENUE", "CONSUMER AFFAIRS",
			"HIGHER EDUCATION" };
	Spinner impspin, tagspin;
	String line, tag;
	float rating;
	ArrayAdapter<String> prob;
	ImageButton button;
	String loc = null;
	double lati, longi;
	Button submit;
	EditText desc;
	int connflag;
	InputMethodManager inputManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		inputManager = (InputMethodManager) getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		View v = inflater.inflate(R.layout.activity_main, container, false);
		impspin = (Spinner) v.findViewById(R.id.spimp);
		tagspin = (Spinner) v.findViewById(R.id.sptag);
		tagspin.setOnItemSelectedListener(this);
		submit = (Button) v.findViewById(R.id.bamsubmit);
		desc = (EditText) v.findViewById(R.id.etamdesc);
		button = (ImageButton) v.findViewById(R.id.bmap);
		submit.setOnClickListener(this);
		ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, imp);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		impspin.setAdapter(aa);
		impspin.setOnItemSelectedListener(this);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				networkcheck();
				if (connflag == 1) {
					startActivityForResult(new Intent(getActivity(),
							Maps_Test.class), 1);
				} else {
					Toast.makeText(getActivity(), "CHECK NETWORK CONNECTION",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		return v;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
		case R.id.spimp:
			if (pos == 0)
				prob = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_item, tl1);
			if (pos == 1)
				prob = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_item, tl2);
			if (pos == 2)
				prob = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_item, tl3);
			prob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tagspin.setAdapter(prob);
			break;
		case R.id.sptag:
			tag = parent.getItemAtPosition(pos).toString();
			// Toast.makeText(this, tag, Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		if (connflag == 1) {
			if (desc.getText().toString().isEmpty()) {
				Toast.makeText(getActivity(), "ENTER A DESCRIPTION",
						Toast.LENGTH_LONG).show();
			} else if (loc == null) {
				Toast.makeText(getActivity(), "CHOOSE A LOCATION",
						Toast.LENGTH_LONG).show();
			} else
				submit();
		} else
			Toast.makeText(getActivity(), "CHECK NETWORK CONNECTION",
					Toast.LENGTH_LONG).show();

	}

	public void networkcheck() {
		ConnectivityManager connectivity = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] info = connectivity.getAllNetworkInfo();
		if (info != null)
			for (int i = 0; i < info.length; i++)
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					connflag = 1;
					break;
				}
	}

	void submit() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				parser();
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

			}

		}.execute(null, null, null);

	}

	public void parser() {
		String url = "http://test-rkr.appspot.com";
		ArrayList<NameValuePair> paramet = new ArrayList<NameValuePair>();
		paramet.add(new BasicNameValuePair("PROB_TAG", tag));
		paramet.add(new BasicNameValuePair("LAT", "" + lati));
		paramet.add(new BasicNameValuePair("LONG", "" + longi));
		HttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(paramet));
			HttpResponse response = client.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				StringBuilder builder = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				line = builder.toString();

			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == 1) {

				loc = data.getStringExtra("loc");
				lati = data.getDoubleExtra("lati", 0);
				longi = data.getDoubleExtra("longi", 0);
			}
		}
	}

}
