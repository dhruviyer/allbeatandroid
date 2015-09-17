package co.allbeat.app;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class PlayActivity extends Activity {
	Button playButton;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		playButton = (Button) findViewById(R.id.playButton);
		tv = (TextView) findViewById(R.id.textView1);

		playButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// ALERT MESSAGE
				Handler m = new Handler(Looper.getMainLooper());
				
				Runnable r = new Runnable() {
					public void run() {

						Toast.makeText(getBaseContext(),
								"Please wait, connecting to server.",
								Toast.LENGTH_LONG).show();

						HttpClient Client = new DefaultHttpClient();
						String URL = "http://api.allbeat.co/songs/random";
						Log.i("httpget", URL);
						try {
							String SetServerString = "";
							HttpGet httpget = new HttpGet(URL);
							ResponseHandler<String> responseHandler = new BasicResponseHandler();
							SetServerString = Client.execute(httpget,
									responseHandler);
							tv.setText(SetServerString);

						} catch (Exception ex) {
							tv.setText("Fail!");
							Log.e("error", "Darn", ex);
						}
					}
				};
				m.post(r);
				
				
			}
		});

	}
}
