package com.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private EditText search;
	private TextView result;
	private ImageView image;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (EditText) findViewById(R.id.searchText);
        result = (TextView) findViewById(R.id.result);
        new Color();
		result.setTextColor(Color.BLACK);
        image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.skrald);
    }
	
	public void getSearchText(View view){
		setResultText(search.getText().toString());
	}
	
	private void setResultText(String search){
		if(search.toLowerCase().trim().equals("banan")){
			result.setText("Organisk");
			result.setBackgroundColor(new Color().GREEN);
			image.setImageResource(R.drawable.banan);
		}else if(search.toLowerCase().trim().equals("brød")){
			result.setText("Organisk");
			result.setBackgroundColor(new Color().GREEN);
			image.setImageResource(R.drawable.broed);
		}else if(search.toLowerCase().trim().equals("plastikkrus")){
			result.setText("Uorganisk");
			result.setBackgroundColor(new Color().LTGRAY);
			image.setImageResource(R.drawable.plastik);
		}else {
			result.setText("");
			image.setImageResource(R.drawable.skrald);
		}
	}
	
	public void getInfo(View view){
		startActivity(new Intent(this, InfoActivity.class));
	}
	
	public void setMap(View view){
		image.setImageResource(R.drawable.haderslev);
		result.setText("");
		search.setText("");
	}
}
