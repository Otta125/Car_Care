package com.pop.carcare.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.pop.carcare.MainActivity;
import com.pop.carcare.R;
import com.squareup.picasso.Picasso;

import static com.pop.carcare.Utilities.Constants.FIRST_LOGIN_INTENT;

public class SplashActivity extends AppCompatActivity {

    private Context mContext;
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        splashImage = findViewById(R.id.splash);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //double height = displayMetrics.heightPixels;
        double width = displayMetrics.widthPixels;
        Picasso.get()
                .load(R.drawable.logo)
                .resize((int) (width*0.50), (int) (width*0.50))
                .centerInside()
                .into(splashImage);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        new Handler().postDelayed(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {

                if (!prefs.getBoolean(FIRST_LOGIN_INTENT, false)) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    Intent x = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(x);
                }

                // close this activity
                finish();
            }
        }, 5 * 1000); // wait for 5 seconds
    }
}
