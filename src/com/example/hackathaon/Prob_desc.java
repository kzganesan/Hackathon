package com.example.hackathaon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Prob_desc extends Activity {
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prob_desc);
		tv=(TextView)findViewById(R.id.tvprobdesc);
		tv.setText(getIntent().getStringExtra("desc"));
	}

}
