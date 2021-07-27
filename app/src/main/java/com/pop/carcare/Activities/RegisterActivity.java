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
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.pop.carcare.Database.AppDatabase;
import com.pop.carcare.Database.AppExecutors;
import com.pop.carcare.Database.model.Users;
import com.pop.carcare.MainActivity;
import com.pop.carcare.R;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class RegisterActivity extends AppCompatActivity {
    private Context mContext;
    private AppDatabase mDb;
    EditText txtName, txtEmail, txtPass;
    AwesomeValidation mAwesomeValidation;
    Integer ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        txtName = findViewById(R.id.name);
        txtEmail = findViewById(R.id.email);
        txtPass = findViewById(R.id.pass);
        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);

        mAwesomeValidation.addValidation(this, R.id.name, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(this, R.id.pass, "^.{6,}$", R.string.err_password);
        mAwesomeValidation.addValidation(this, R.id.re_pass, R.id.pass, R.string.err_password_confirmation);

        mDb = AppDatabase.getInstance(getApplicationContext());
        findViewById(R.id.regs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAwesomeValidation.validate()) {
                    final LiveData<Users> user_email = mDb.taskDao().loadUserByEmail(txtEmail.getText().toString());
                    user_email.observe(RegisterActivity.this, new Observer<Users>() {
                        @Override
                        public void onChanged(@Nullable Users users) {
                            user_email.removeObserver(this);
                            if (users != null) {
                                Toast.makeText(mContext, "Email is Exist", Toast.LENGTH_SHORT).show();
                            }else {
                                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        Users users = new Users(txtName.getText().toString(), txtEmail.getText().toString(),
                                                txtPass.getText().toString());
                                        Long id = mDb.taskDao().insertUser(users);
                                        Log.e("IDD", String.valueOf(id));
                                        ID =(int) (long) id;

                                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                                        SharedPreferences.Editor editors = prefs.edit();
                                        editors.putBoolean("first_time", true);
                                        editors.putInt("id",ID);
                                        editors.apply();
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
