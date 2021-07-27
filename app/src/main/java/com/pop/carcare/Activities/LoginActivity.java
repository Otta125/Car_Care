package com.pop.carcare.Activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.pop.carcare.Database.AppDatabase;
import com.pop.carcare.Database.model.Users;
import com.pop.carcare.MainActivity;
import com.pop.carcare.R;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;
import static com.pop.carcare.Utilities.Constants.FIRST_LOGIN_INTENT;
import static com.pop.carcare.Utilities.Constants.ID_INTENT;

public class LoginActivity extends AppCompatActivity {

    private Context mContext;
    EditText txtPass, txtEmail;
    AwesomeValidation mAwesomeValidation;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        txtEmail = findViewById(R.id.email);
        txtPass = findViewById(R.id.password);
        mDb = AppDatabase.getInstance(getApplicationContext());
        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);
        mAwesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(this, R.id.password,
                "^.{6,}$",
                R.string.err_password);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAwesomeValidation.validate()){
                final LiveData<Users> user_email = mDb.taskDao().loadUserByEmail(txtEmail.getText().toString());
                user_email.observe(LoginActivity.this, new Observer<Users>() {
                    @Override
                    public void onChanged(@Nullable Users users) {
                        user_email.removeObserver(this);
                        if (users != null) {
                            final LiveData<Users> user_password = mDb.taskDao().loadUserByPassword(txtPass.getText().toString());
                            user_password.observe(LoginActivity.this, new Observer<Users>() {
                                @Override
                                public void onChanged(@Nullable Users users) {
                                    user_password.removeObserver(this);
                                    if (users != null) {
                                        Toast.makeText(mContext, "Welcome", Toast.LENGTH_SHORT).show();
                                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                        SharedPreferences.Editor editors = prefs.edit();
                                        editors.putBoolean(FIRST_LOGIN_INTENT, true);
                                        editors.putInt(ID_INTENT,users.getId());
                                        editors.apply();
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
            }
            }
        });
    }



    public void regNow(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
