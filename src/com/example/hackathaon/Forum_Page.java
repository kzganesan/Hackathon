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

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Forum_Page extends Fragment implements OnItemClickListener {
	ListView lv;
	ArrayList<String> tags = new ArrayList<String>();
	ArrayList<String> desc = new ArrayList<String>();
	String line;
	IconicAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.forum_page, container, false);
		lv = (ListView) v.findViewById(R.id.lvforum);
		adapter = new IconicAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		postdata();
		return v;
	}

	class IconicAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tags.size();
		}

		@Override
		public String getItem(int arg0) {
			// TODO Auto-generated method stub
			return tags.get(arg0);
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
				v = getActivity().getLayoutInflater().inflate(
						R.layout.forumlistelement, parent, false);
			}
			VeiwHolder holder = (VeiwHolder) v.getTag();
			if (holder == null) {
				holder = new VeiwHolder(v);
				v.setTag(holder);
			}
			TextView tv = (TextView) v.findViewById(R.id.tvfprob);
			tv.setTextColor(getResources().getColor(android.R.color.white));
			tv.setTextSize(18);
			// holder.prob.setText(getItem(pos));
			tv.setText(tags.get(pos));
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
					tags.add(tmpi[3]);
					Log.d("tagi", tmpi[3]);
					desc.add(tmpi[4]);
					adapter.notifyDataSetChanged();

				}

				// tv.setText(line);
			}

		}.execute(null, null, null);

	}

	public void parser() {
		String url = "http://test-rkr.appspot.com";
		ArrayList<NameValuePair> paramet = new ArrayList<NameValuePair>();
		Log.d("uid", getArguments().getString("uid"));
		paramet.add(new BasicNameValuePair("uid", getArguments().getString("uid")));
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		startActivity(new Intent(getActivity(), Prob_desc.class).putExtra("desc", desc.get(arg2)));
	}
}
