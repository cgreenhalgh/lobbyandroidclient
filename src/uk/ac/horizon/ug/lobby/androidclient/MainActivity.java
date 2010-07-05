package uk.ac.horizon.ug.lobby.androidclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
	static final String TAG = "LobbyMain";
	
	static final int DIALOG_ERROR = 1;

	protected String imei;
	private static int instance = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // get device unique ID(s)
        TelephonyManager mTelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        imei = mTelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE  
        //String phoneNumber=mTelephonyMgr.getLine1Number(); 
        TextView imeiTextView = (TextView)findViewById(R.id.imei_text_view);
        imeiTextView.setText(imei);

        // standard launch mode => per intent ?!
        Intent intent = getIntent();
		Uri data = intent.getData();
        TextView uriTextView = (TextView)findViewById(R.id.uri_text_view);
        uriTextView.setText(data.toString());
    
        TextView instanceTextView = (TextView)findViewById(R.id.main_instance);
        instanceTextView.setText(""+(instance++));

        try {
        	File file = new File(new URI(data.toString()));
        	BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        	String line = fr.readLine();
        	JSONObject json = new JSONObject(line);
            TextView configTextView = (TextView)findViewById(R.id.main_config);
            configTextView.setText(json.toString());
            fr.close();
        }
        catch (Exception e) {
        	Log.e(TAG, "Error opening data "+data, e);
        }
    }

    
    /** losing focus - persist... */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/** gaining focus */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
}