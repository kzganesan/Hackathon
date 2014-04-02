package com.example.hackathaon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class User_Reg extends Activity {
	EditText etname, etno;
	Button sub;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_reg);
		etname = (EditText) findViewById(R.id.eturname);
		etno = (EditText) findViewById(R.id.eturno);
		sub = (Button) findViewById(R.id.bursub);
	}

}
