package com.example.hackathaon;

import android.view.View;
import android.widget.TextView;

public class VeiwHolder {
	TextView prob;

	public VeiwHolder(View v) {
		prob = (TextView) v.findViewById(R.id.tvfprob);

	}
}
