package co.allbeat.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.session.MediaController;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class PlayActivity extends Activity {

	static EditText etResponse;
	static String trackURL;
	Button playBttn;
	static MediaPlayer mPlayer;
	ProgressBar mediaseek;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		// get reference to the views
		playBttn = (Button) findViewById(R.id.playbttn);
		
		playBttn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
						AsyncTask<String, Void, String> task = new HttpAsyncTask().execute("http://104.131.11.1/songs/random");					
				}catch(Exception e){}

			}
		});
	}
	
	public static String GET(String url) {
		InputStream inputStream = null;
		String result = "";
		try {

			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		try {
			JSONObject jObject = new JSONObject(result);
			trackURL = jObject.getString("allbeat_url");
		} catch (Exception e) {
		}

		Log.d("OBJECT", trackURL);
		return result;
	}

	private static void playSong(String url) {
		mPlayer = new MediaPlayer();
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		try {
			mPlayer.setDataSource(url);
			mPlayer.prepare();
		} catch (Exception e) {}

		mPlayer.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				mPlayer.seekTo(20000);
				mPlayer.start();
				CountDownTimer timer = new CountDownTimer(11000,1000) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onFinish() {
						mPlayer.stop();
						mPlayer.release();
						AsyncTask<String, Void, String> task = new PlayActivity().new HttpAsyncTask().execute("http://104.131.11.1/songs/random");
					}
				}.start();
			}
		});
		
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
				playSong(trackURL);
		}
	}
}
