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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Party_Page extends Activity {
	TextView party_name;
	ListView lv;
	String line;
	ArrayList<String> sdesc = new ArrayList<String>();
	IconicAdapter adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.party_page);
		party_name = (TextView) findViewById(R.id.tvpname);
		party_name.setText("BJP");
		adapter = new IconicAdapter();
		lv = (ListView) findViewById(R.id.lvproblist);
		postdata();
	}

	class IconicAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub

			return sdesc.size();
		}

		@Override
		public String getItem(int arg0) {
			// TODO Auto-generated method stub
			return sdesc.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int pos, View v, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (v == null) {
				v = getLayoutInflater().inflate(R.layout.party_prob_layout,
						parent, false);
			}
			TextView tv1 = (TextView) v.findViewById(R.id.tvppprob);
			// holder.prob.setText(getItem(pos));
			if (pos == 0) {
				tv1.setText("MAHARASHTRA");
				tv1.setTextSize(30);
			} else if (pos == 4) {
				tv1.setText("GUJRAT");
				tv1.setTextSize(30);
			} else if (pos == 11) {
				tv1.setText("DELHI");
				tv1.setTextSize(30);
			} else
				tv1.setText(sdesc.get(pos));
			//tv1.setTextColor(getResources().getColor(android.R.color.white));
			// tv.setText(desc.get(pos));
			// tv2.setText(tag.get(pos));
			return v;
		}

	}

	private void postdata() {
		// TODO Auto-generated method stub
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
				Log.d("tag", line);
				String[] tmp = line.split("]");
				for (int i = 0; i < tmp.length; i++) {
					String tmpi[] = tmp[i].split(",");
					sdesc.add(tmpi[4]);
					Log.d("tagi", tmpi[4]);
					lv.setAdapter(adapter);
					// adapter.notifyDataSetChanged();

				}

				// tv.setText(line);
			}

		}.execute(null, null, null);

	}

	public void parser() {
		String url = "http://test-rkr.appspot.com/trending";
		// ArrayList<NameValuePair> paramet = new ArrayList<NameValuePair>();
		// paramet.add(new BasicNameValuePair("uid", "2"));
		HttpClient client = new DefaultHttpClient();
		HttpGet httppost = new HttpGet(url);
		try {
			// httppost.setEntity(new UrlEncodedFormEntity(paramet));
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
}
