package com.pop.carcare.Activities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.pop.carcare.Database.AppDatabase;
import com.pop.carcare.Database.AppExecutors;
import com.pop.carcare.Database.model.Reservations;
import com.pop.carcare.Database.model.Users;
import com.pop.carcare.MainActivity;
import com.pop.carcare.R;
import com.pop.carcare.Utilities.Constants;
import com.pop.carcare.Utilities.MyDatePickerCarFragment;
import com.pop.carcare.Widget.WidgetService;

import java.util.Calendar;


import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;
import static com.pop.carcare.Utilities.Constants.ID_INTENT;

public class ReservationActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Context mContext;

    int User_id;
    String price, name, desc, IMG;
    public static String dateChoosed, Timechhose;
    static TextView txtshowdate, dateex, timex, timeis, nameplace, descplace, title, total;
    EditText phonetxt;
    ImageView imageView;
    static LinearLayout linseledate;
    LinearLayout linseletime;
    AwesomeValidation mAwesomeValidation;
    String phone;
    ////location
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private FusedLocationProviderClient fusedLocationClient;
    private AppDatabase mDb;

    private String Lat;
    private String Long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

///////////////////////////////
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        //////////////////////////////////////////
        mDb = AppDatabase.getInstance(getApplicationContext());

        linseledate = findViewById(R.id.datesele);
        linseletime = findViewById(R.id.timesele);
        mContext = this;

        txtshowdate = findViewById(R.id.datechoosee);
        timex = findViewById(R.id.timechoose);
        timeis = findViewById(R.id.timechoosee);
        txtshowdate.setVisibility(View.GONE);
        timeis.setVisibility(View.GONE);
        dateex = findViewById(R.id.datechoose);
        phonetxt = findViewById(R.id.phoneid);
        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);
        mAwesomeValidation.addValidation(this, R.id.phoneid, "[0-9]{5,}$", R.string.wrong_num);
        nameplace = findViewById(R.id.carnaem);
        descplace = findViewById(R.id.cardesc);
        title = findViewById(R.id.toolbar_title);
        title.setText(R.string.book);
        total = findViewById(R.id.totalM);
        imageView = findViewById(R.id.imgback);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            price = bundle.getString(Constants.PRICE_INTENT);
            name = bundle.getString(Constants.NAME_INTENT);
            IMG= bundle.getString(Constants.IMG_INTENT);
        }
        nameplace.setText(name);
        descplace.setText(desc);
        total.setText(getString(R.string.price) + " " + getString(R.string.le));
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplication());
        User_id = prefs.getInt(ID_INTENT, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public static void getDate(String datec) {
        dateex.setText(datec);
        dateChoosed = datec;
        txtshowdate.setVisibility(View.VISIBLE);
        linseledate.setVisibility(View.VISIBLE);

    }

    public void Date_Care(View view) {
        DialogFragment newFragment = new MyDatePickerCarFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.date_picker));
    }

    public void Time_car(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (selectedMinute < 10) {
                    timex.setText(selectedHour + getString(R.string.zero) + selectedMinute);
                    Timechhose = selectedHour + getString(R.string.zero)  + selectedMinute;
                    timeis.setVisibility(View.VISIBLE);
                    linseletime.setVisibility(View.VISIBLE);
                } else {
                    timex.setText(selectedHour + getString(R.string.separate) + selectedMinute);
                    Timechhose = selectedHour + getString(R.string.separate)+ selectedMinute;
                    timeis.setVisibility(View.VISIBLE);
                    linseletime.setVisibility(View.VISIBLE);
                }
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle(getString(R.string.select_time));
        mTimePicker.show();
    }

    public void BookCar(View view) {

        phone = String.valueOf(phonetxt.getText());

        if (phone == null || phone.matches("") || !mAwesomeValidation.validate()) {

            Toast.makeText(ReservationActivity.this, R.string.enter_name, Toast.LENGTH_SHORT).show();

        } else if (dateChoosed == null) {
            Toast.makeText(ReservationActivity.this, R.string.enter_date, Toast.LENGTH_SHORT).show();

        } else {
            StringBuilder sb = new StringBuilder(1000);
            sb.append(getString(R.string.service_name)+name)
                    .append("\n ")
                    .append(getString(R.string.service_price)+price+getString(R.string.le))
                    .append("\n ")
                    .append(getString(R.string.date_of_reservstion)+dateChoosed+" "
                            +getString(R.string.time_of_reservation)
                            +Timechhose)
                    .append("\n");
            SharedPreferences.Editor editor = mContext.getSharedPreferences(Constants.Recipe_ID_FOR_Widget, MODE_PRIVATE).edit();
            editor.putString(Constants.Recipe_ID_FOR_Widget, sb.toString());
            editor.apply();
            WidgetService.startActionWaterPlant(mContext);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Reservations reservations = new Reservations(name,price,dateChoosed+" "+Timechhose,phone,User_id,String.valueOf(currentLatitude),String.valueOf(currentLongitude));
                    mDb.taskDao().insertReservation(reservations);
                    finish();
                }
            });

        }

    }

    ////////////////////////////
    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            currentLatitude = location.getLatitude();
                            currentLongitude = location.getLongitude();
                        }
                    }

                });
        fusedLocationClient.getLastLocation().addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);


            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
    }


}
