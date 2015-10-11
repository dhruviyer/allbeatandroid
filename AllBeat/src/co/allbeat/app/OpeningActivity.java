package co.allbeat.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class OpeningActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opening);
		
		CountDownTimer timer = new CountDownTimer(3000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				Intent i = new Intent(OpeningActivity.this, MainActivity.class);
				startActivity(i);
				OpeningActivity.this.finish();
				
				
			}
		};
	}
}
