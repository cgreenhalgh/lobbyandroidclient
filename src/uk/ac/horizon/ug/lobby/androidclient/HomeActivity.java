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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author cmg
 *
 */
public class HomeActivity extends Activity implements OnClickListener {

	static final int DIALOG_ERROR = 1;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button button = (Button)findViewById(R.id.go_to_default);
        button.setOnClickListener(this);
	}

	/** button */
	@Override
	public void onClick(View view) {
		try {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse(this.getString(R.string.default_lobby_uri)), getString(R.string.lobby_mime_type));
			startActivity(intent);
		}
		catch (Exception e) {
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
	
	

}
