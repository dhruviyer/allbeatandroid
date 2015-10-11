package co.allbeat.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * @author Dhruv
 * 
 * v1.0 Simple UI, press a button, start listening
 *
 */
public class MainActivity extends Activity{
	ImageView discoverButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialize buttons
		discoverButton = (ImageView) findViewById(R.id.discover);
		
	}
	
	public void startPlaying(View v){
		Intent intent = new Intent(this, PlayActivity.class);
		startActivity(intent);
	}
}

