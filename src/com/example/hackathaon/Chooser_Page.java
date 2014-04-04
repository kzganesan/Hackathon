package com.example.hackathaon;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Chooser_Page extends ActionBarActivity {
	int check;
	ActionBarDrawerToggle toggle;
	DrawerLayout drawer;
	ListView lv;
	String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooder_page);
		uid = getIntent().getStringExtra("uid");
		drawer = (DrawerLayout) findViewById(R.id.dlchp);
		toggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_drawer,
				R.string.app_name, R.string.app_name) {
			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
				// getSupportActionBar().setTitle("SELECT IMAGE");
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);

			}

			@Override
			public void onDrawerStateChanged(int newState) {
				// TODO Auto-generated method stub
				super.onDrawerStateChanged(newState);
			}
		};
		drawer.setDrawerListener(toggle);
		//drawer.openDrawer(Gravity.END);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		lv = (ListView) findViewById(R.id.dlchlist);
		String[] list = { "RECOMMENDATIONS", "FORUM", "REPORT A PROBLEM" };
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list));
		lv.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				if (pos == 0) {
					Bundle args = new Bundle();
					args.putString("uid", getIntent().getStringExtra("uid"));
					Forum_Page frag = new Forum_Page();
					frag.setArguments(args);
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.cp_frame, frag).commit();
					drawer.closeDrawers();
				}
				if (pos == 1) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.cp_frame, new Public_Forum())
							.commit();
					drawer.closeDrawers();
				}
				if (pos == 2) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.cp_frame, new MainActivity())
							.commit();
					drawer.closeDrawers();
				}
			}
		});
	    //getSupportFragmentManager().beginTransaction().replace(R.id.cp_frame, new Public_Forum()).commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (toggle.onOptionsItemSelected(item))
			return true;
		return false;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		toggle.syncState();
	}
}
