/**
 * 
 */
package uk.ac.horizon.ug.lobby.androidclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

/**
 * @author cmg
 *
 */
public class HomeActivity extends Activity implements OnClickListener {
	static final String TAG = "LobbyHome";
	
	private WebView mWebView;
	private Handler mHandler = new Handler(); 
	   
	static final int DIALOG_ERROR = 1;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
//        Button button = (Button)findViewById(R.id.go_to_default);
//        button.setOnClickListener(this);
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(false);          
        webSettings.setSaveFormData(false);         
        webSettings.setJavaScriptEnabled(true);       
        webSettings.setSupportZoom(false);      

        mWebView.addJavascriptInterface(new TestJavaScriptInterface(), "test");    
        
        mWebView.loadUrl("file:///android_asset/test.html");	
    }

	class TestJavaScriptInterface {
		//mHandler.post(new Runnable() {   
		// public void run() {                     
		// mWebView.loadUrl("javascript:wave()");   
		//}
		public void hello() {
			Log.i(TAG,"hello javascript!");
		}
	}
	
	
	/** button */
	@Override
	public void onClick(View view) {
		try {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse(this.getString(R.string.default_lobby_uri)), getString(R.string.lobby_mime_type));
			//intent.addCategory(Intent.CATEGORY_DEFAULT);
			intent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
			//intent.addFlags(Intent.FL)
			this.startActivity(intent);
		}
		catch (Exception e) {
			Log.e(TAG, "Opening "+getString(R.string.default_lobby_uri), e);
			showDialog(DIALOG_ERROR);
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ERROR:
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Unable to start Lobby client");
			builder.setCancelable(true);
			AlertDialog alert = builder.create();
			return alert;
		}
		default:
			return null;
		}
	}
	
	
	/**       
	 * Provides a hook for calling "alert" from javascript. Useful for       
	 * debugging your javascript.       
	 */    
	final class MyWebChromeClient extends WebChromeClient {
		@Override          
		public boolean onJsAlert(WebView view, String url, String message, JsResult result) {   
			Log.d(TAG, message);            
			result.confirm();     
			return true;     
		}
	}  
}
