package com.example.hackathaon;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class User_Profile extends Activity implements OnClickListener {
	EditText etudob, etuvid, etuprof, etuloc;
	Spinner spuc, etuinc, etums;
	String[] marital_status = { "Married", "Single" };
	String[] caste = { "OBC", "SC/ST", "General" };
	String[] income = { "HIGH", "MEDIUM", "LOW" };
	Calendar c = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		etudob = (EditText) findViewById(R.id.etudob);
		etudob.setOnClickListener(this);
		etuvid = (EditText) findViewById(R.id.etuvid);
		etuprof = (EditText) findViewById(R.id.etuprof);
		etuloc = (EditText) findViewById(R.id.etuloc);
		spuc = (Spinner) findViewById(R.id.spuc);
		etuinc = (Spinner) findViewById(R.id.etuinc);
		etums = (Spinner) findViewById(R.id.etums);
		ArrayAdapter<String> adapterm = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, marital_status);
		adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		etums.setAdapter(adapterm);
		ArrayAdapter<String> adapterc = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, caste);
		adapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spuc.setAdapter(adapterc);
		ArrayAdapter<String> adapteri = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, income);
		adapteri.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		etuinc.setAdapter(adapteri);
	}

	DatePickerDialog.OnDateSetListener dl = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, monthOfYear);
			c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			etudob.setText(DateUtils.formatDateTime(User_Profile.this,
					c.getTimeInMillis(), DateUtils.FORMAT_ABBREV_ALL));

		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		new DatePickerDialog(this, dl, c.get(Calendar.YEAR),
				c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
	}
}
