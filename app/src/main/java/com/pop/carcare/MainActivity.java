package com.pop.carcare;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.navigation.NavigationView;
import com.pop.carcare.Activities.MyOrdersActivity;
import com.pop.carcare.Activities.ReservationActivity;
import com.pop.carcare.Adapters.ServiceAdapter;
import com.pop.carcare.Database.AppDatabase;
import com.pop.carcare.Database.AppExecutors;
import com.pop.carcare.Database.MainViewModel;
import com.pop.carcare.Database.model.Reservations;
import com.pop.carcare.Database.model.Services;
import com.pop.carcare.Database.model.Users;
import com.pop.carcare.Utilities.AnalyticsApplication;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppDatabase mDb;
    private List<Services> servicesList;
    ProgressBar progressBar;
    RecyclerView ServiceRecycle;
    private ServiceAdapter serviceAdapter;
    Context mContext;
    NavigationView navigationView;
    private Tracker mTracker;

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Main Car Care");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        /////////////
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        /////////////
        progressBar = findViewById(R.id.progressBar2);
        ServiceRecycle = findViewById(R.id.crsrec);
        ServiceRecycle.setLayoutManager(new LinearLayoutManager(this));
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = findViewById(R.id.mainn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.logo));


        mDb = AppDatabase.getInstance(getApplicationContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Services services = new Services("washing", "50.00", "https://anewwayforward.org/wp-content/uploads/2019/11/BEST-CAR-WASH-MOP.jpg");
                mDb.taskDao().insertServices(services);
                Services services2 = new Services("Fix", "60.00", "https://mycarneedsa.com/assets/uploads/images/blog/3366f9345c5223e0b533a073b2931225.png");
                mDb.taskDao().insertServices(services2);
                Services services3 = new Services("paint", "70.00", "https://www.africa-uganda-business-travel-guide.com/images/how-to-maintain-your-car-paint-in-uganda-21846914.jpg");
                mDb.taskDao().insertServices(services3);
                Services services4 = new Services("polish", "80.00", "https://www.thevehiclelab.com/wp-content/uploads/2019/04/polishing-or-waxing-car-1.png");
                mDb.taskDao().insertServices(services4);
                Services services5 = new Services("mechanic", "90.00", "https://images.netdirector.co.uk/gforces-auto/image/upload/w_412,h_309,dpr_2.0,q_auto:best,c_fill,f_auto,fl_lossy/auto-client/efe83977636d46864c2acd646fbbc7a7/banner.jpg");
                mDb.taskDao().insertServices(services5);
            }
        });

        MainViewModel viewModel = ViewModelProviders.of(MainActivity.this).get(MainViewModel.class);
        viewModel.getServices().observe(MainActivity.this, new Observer<List<Services>>() {
            @Override
            public void onChanged(@Nullable List<Services> services) {
                servicesList = new ArrayList<>();
                servicesList.clear();
                if (services != null) {
                    for (int i = 0; i < services.size(); i++) {
                        servicesList.add(services.get(i));
                        Log.e("NAme", " : " + services.get(i).getName());
                    }
                }
                serviceAdapter = new ServiceAdapter(mContext, servicesList);
                ServiceRecycle.setAdapter(serviceAdapter);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_item_one) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("Orders")
                    .build());
            Intent intent = new Intent(this, MyOrdersActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
