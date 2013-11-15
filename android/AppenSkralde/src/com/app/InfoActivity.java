package com.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends Activity {

	private TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		text = (TextView) findViewById(R.id.info);
		text.setText("Information omkring provas!");
	}
	
	public void back(View view){
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
