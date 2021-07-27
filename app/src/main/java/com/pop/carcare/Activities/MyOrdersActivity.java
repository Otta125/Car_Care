package com.pop.carcare.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.pop.carcare.Adapters.ReservationsAdapter;
import com.pop.carcare.Adapters.ServiceAdapter;
import com.pop.carcare.Database.AppDatabase;
import com.pop.carcare.Database.MainViewModel;
import com.pop.carcare.Database.model.Reservations;
import com.pop.carcare.Database.model.Services;
import com.pop.carcare.MainActivity;
import com.pop.carcare.R;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {

    private List<Reservations> reservationsList;
    RecyclerView reservationRescycle;
    private ReservationsAdapter reservationsAdapter;
    private AppDatabase mDb;
    Context mContext;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        mContext = this;
        imageView = findViewById(R.id.imgback);

        mDb = AppDatabase.getInstance(getApplicationContext());
        reservationRescycle = findViewById(R.id.orders);
        reservationRescycle.setLayoutManager(new LinearLayoutManager(this));
        MainViewModel viewModel = ViewModelProviders.of(MyOrdersActivity.this).get(MainViewModel.class);
        viewModel.getReservations().observe(MyOrdersActivity.this, new Observer<List<Reservations>>()
        {
            @Override
            public void onChanged(@Nullable List<Reservations> reservations) {
                reservationsList = new ArrayList<>();
                reservationsList.clear();
                if (reservations != null) {
                    for (int i = 0; i < reservations.size(); i++) {
                        reservationsList.add(reservations.get(i));
                    }
                }
                reservationsAdapter = new ReservationsAdapter(mContext, reservationsList);
                reservationRescycle.setAdapter(reservationsAdapter);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

