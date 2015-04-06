package com.rssfeed.activity;

import com.rssfeed.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
private static final int SPLASH_DISPLAY_TIME = 4000; // splash screen delay time

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.intro);

    new Handler().postDelayed(new Runnable() {
        public void run() {

            Intent intent = new Intent();
            intent.setClass(SplashScreen.this, AndroidTabAndListView.class);

            SplashScreen.this.startActivity(intent);
            SplashScreen.this.finish();

            // transition from splash to main menu
            overridePendingTransition(R.anim.activityfadein,
                    R.anim.splashfadeout);

        }
    }, SPLASH_DISPLAY_TIME);
}
}