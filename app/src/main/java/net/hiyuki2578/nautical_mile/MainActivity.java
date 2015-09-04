package net.hiyuki2578.nautical_mile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends ActionBarActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath("fonts/Koruri-Regular.ttf")
				.setFontAttrId(R.attr.fontPath)
				.build()
		);
		setContentView(R.layout.activity_main);
		EditText edit = (EditText)findViewById(R.id.editText);
		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				EditText edit = (EditText)findViewById(R.id.editText);
				SpannableStringBuilder sp = (SpannableStringBuilder)edit.getText();
				TextView text = (TextView)findViewById(R.id.mile);
				TextView text1 = (TextView)findViewById(R.id.textView2);
				if(sp.toString().equals("")){
					text.setText("");
				}else{
					float float_text = Float.parseFloat(sp.toString());
					String ans = calc(float_text);
					String[] cal = ans.split(",",0);
					text.setText(cal[0]);
					text1.setText(cal[1]);
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
		alertDialog.setIcon(R.mipmap.ic_launcher);
		alertDialog.setTitle(R.string.Exit);
		alertDialog.setMessage(R.string.exit_menu);
		alertDialog.setPositiveButton(R.string.Exit,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		switch (item.getItemId()) {
			case R.id.action_settings:
				Intent intent1 = new Intent(this, Setting.class);
				startActivity(intent1);
				return true;
			case R.id.exit:
				alertDialog.show();
				return true;
			default:

		}

		return super.onOptionsItemSelected(item);
	}
	private String calc(float num){
		SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
		String default_str = "0.539956803,kilo_to_mile";
		String pref = spf.getString("unit", default_str);
		String[] prefs = pref.split(",", 0);
		float unit = Float.parseFloat(prefs[0]);
		double cal = num*unit;
		String units;
		String before_units;
		switch (prefs[1]){
			case "kilo_to_mile":
				units = getResources().getString(R.string.mile);
				before_units = getResources().getString(R.string.kilo);
				break;
			case "mile_to_kilo":
				units = getResources().getString(R.string.kilo);
				before_units = getResources().getString(R.string.mile);
				break;
			case "feet_to_meter":
				units = getResources().getString(R.string.meter);
				before_units = getResources().getString(R.string.feet);
				break;
			case "meter_to_feet":
				units = getResources().getString(R.string.feet);
				before_units = getResources().getString(R.string.meter);
				break;
			case "knots_to_kmh":
				units = getResources().getString(R.string.kmh);
				before_units = getResources().getString(R.string.knots);
				break;
			case "kmh_to_knots":
				units = getResources().getString(R.string.knots);
				before_units = getResources().getString(R.string.kmh);
				break;
			default:
				units = getResources().getString(R.string.mile);
				before_units = getResources().getString(R.string.kilo);
				break;
		}
		return getResources().getString(R.string.before)+":"+num+before_units+"\n\n"+getResources().getString(R.string.after)+":"+String.valueOf(cal)+units+","+before_units;
	}
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
		String default_str = "0.539956803,kilo_to_mile";
		String pref = spf.getString("unit", default_str);
		String[] prefs = pref.split(",",0);
		String before_units;
		switch (prefs[1]){
			case "kilo_to_mile":
				before_units = getResources().getString(R.string.kilo);
				break;
			case "mile_to_kilo":
				before_units = getResources().getString(R.string.mile);
				break;
			case "feet_to_meter":
				before_units = getResources().getString(R.string.feet);
				break;
			case "meter_to_feet":
				before_units = getResources().getString(R.string.meter);
				break;
			case "knots_to_kmh":
				before_units = getResources().getString(R.string.knots);
				break;
			case "kmh_to_knots":
				before_units = getResources().getString(R.string.kmh);
				break;
			default:
				before_units = getResources().getString(R.string.kilo);
				break;
		}
		TextView text = (TextView)findViewById(R.id.textView2);
		text.setText(before_units);
		TextView text1 = (TextView)findViewById(R.id.textView);
		String input = getResources().getString(R.string.prease_input);
		text1.setText(input);
	}
	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
			alertDialog.setIcon(R.mipmap.ic_launcher);
			alertDialog.setTitle(R.string.Exit);
			alertDialog.setMessage(R.string.exit_menu);
			alertDialog.setPositiveButton(R.string.Exit, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			alertDialog.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void attachBaseContext(Context newBase){
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}
}
