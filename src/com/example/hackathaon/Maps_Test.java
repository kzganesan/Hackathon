package com.example.hackathaon;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps_Test extends FragmentActivity {
	GoogleMap map;
	Marker marker;
	TextView tv;
	Button b;
	double lati, longi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps_test);
		tv = (TextView) findViewById(R.id.tvloc);
		b = (Button) findViewById(R.id.bconloc);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Maps_Test.this, MainActivity.class)
						.putExtra("loc", tv.getText().toString())
						.putExtra("lati", lati)
						.putExtra("longi", longi)
						.addFlags(
								Intent.FLAG_ACTIVITY_CLEAR_TASK
										| Intent.FLAG_ACTIVITY_NEW_TASK));
			}
		});
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		map.setBuildingsEnabled(true);
		//map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		if (map == null)
			Toast.makeText(this, "Google Maps not available", Toast.LENGTH_LONG)
					.show();
		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng point) {
				// TODO Auto-generated method stub

				String address = getaddress(point);
				if (marker != null)
					marker.remove();
				marker = map.addMarker(new MarkerOptions().position(point));
				tv.setText(address);
				tv.setVisibility(View.VISIBLE);
				b.setVisibility(View.VISIBLE);
			}
		});
	}

	protected String getaddress(final LatLng point) {
		// TODO Auto-generated method stub
		String address = null;
		lati = point.latitude;
		longi = point.longitude;
		try {
			address = new AsyncTask<Void, Void, String>() {

				String add;

				@Override
				protected String doInBackground(Void... arg0) {
					// TODO Auto-generated method stub
					Geocoder geocoder = new Geocoder(Maps_Test.this,
							Locale.getDefault());
					List<Address> addresses = null;
					try {
						addresses = geocoder.getFromLocation(point.latitude,
								point.longitude, 1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (addresses != null && addresses.size() > 0) {
						Address a = addresses.get(0);
						add = String.format(
								"%s, \n%s",
								a.getMaxAddressLineIndex() > 0 ? a
										.getAddressLine(0) : "", a
										.getLocality());
					}
					return add;
				}

			}.execute(null, null, null).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}

}
