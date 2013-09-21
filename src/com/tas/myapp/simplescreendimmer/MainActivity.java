package com.tas.myapp.simplescreendimmer;


import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;



public class MainActivity extends Activity {

	private static final String BRIGHTNESS_PREFERENCE_KEY = "brightness";
	private View brightnessPanel;
	private SeekBar brightnessControl;
	private int brightness = 50;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		brightnessPanel = findViewById(R.id.panel);
		brightnessControl = (SeekBar) findViewById(R.id.seek);
		Button btn = (Button) findViewById(R.id.bShowBrightness);

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showBrightnessPanel();
			}

		});

		brightnessControl
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						setBrightness(progress);

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						hideBrightnessPanel();

					}

				});

	}

	private void showBrightnessPanel() {
		Animation animation = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left);
		brightnessControl.setProgress(this.brightness);
		brightnessPanel.setVisibility(View.VISIBLE);
		brightnessPanel.startAnimation(animation);

	}

	private void setBrightness(int value) {
		if (value < 10) {
			value = 10;
		} else if (value > 100) {
			value = 100;
		}
		
		
		
		brightness = value;
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.screenBrightness = (float) value / 100;
		lp.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		getWindow().setAttributes(lp);

	}

	private void hideBrightnessPanel() {
		Animation animation = AnimationUtils.loadAnimation(MainActivity.this,
				android.R.anim.slide_out_right);
		brightnessPanel.startAnimation(animation);
		brightnessPanel.setVisibility(View.GONE);
		PreferenceManager
				.getDefaultSharedPreferences(this)
				.edit()
				.putInt(BRIGHTNESS_PREFERENCE_KEY,
						brightnessControl.getProgress()).commit();
	}

}
