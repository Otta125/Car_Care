package com.pop.carcare.Activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pop.carcare.R;
import com.pop.carcare.Utilities.Constants;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    String Name, Price, Img;
    ImageView img, back;
    TextView Nametxt, Pricetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        img = findViewById(R.id.img);
        back = findViewById(R.id.imgback);
        Nametxt = findViewById(R.id.name);
        Pricetxt = findViewById(R.id.price);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Name = bundle.getString(Constants.NAME_INTENT);
            Price = bundle.getString(Constants.PRICE_INTENT);
            Img = bundle.getString(Constants.IMG_INTENT);
        }

        Nametxt.setText(Name);
        Pricetxt.setText(Price);

        Picasso.get()
                .load(Img)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(img);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void Book(View view) {
        Intent intent = new Intent(this, ReservationActivity.class);
        intent.putExtra(Constants.NAME_INTENT, Name);
        intent.putExtra(Constants.PRICE_INTENT, Price);
        intent.putExtra(Constants.IMG_INTENT, Img);
        startActivity(intent);
    }

}
