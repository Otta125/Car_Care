package com.pop.carcare.Adapters;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pop.carcare.Activities.DetailsActivity;
import com.pop.carcare.Database.model.Services;
import com.pop.carcare.R;
import com.pop.carcare.Utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServicesViewHolder> {

    private Context mCtx;
    private List<Services> ServicesList;


    public ServiceAdapter(Context mCtx, List<Services> ServicesList) {
        this.mCtx = mCtx;
        this.ServicesList = ServicesList;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card, parent, false);
        return new ServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServicesViewHolder holder, final int position) {

        Picasso.get()
                .load(ServicesList.get(position).getImg())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.img);

        holder.Nametxt.setText(ServicesList.get(position).getName());
        holder.Pricetxt.setText(ServicesList.get(position).getPrice());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, DetailsActivity.class);
                intent.putExtra(Constants.NAME_INTENT, ServicesList.get(position).getName());
                intent.putExtra(Constants.PRICE_INTENT, ServicesList.get(position).getPrice());
                intent.putExtra(Constants.IMG_INTENT, ServicesList.get(position).getImg());

                mCtx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ServicesList.size();
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView Nametxt, Pricetxt;

        public ServicesViewHolder(View itemview) {
            super(itemview);
            img = itemView.findViewById(R.id.image1);
            Nametxt = itemView.findViewById(R.id.name);
            Pricetxt = itemView.findViewById(R.id.price);
        }
    }
}
