package com.pop.carcare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pop.carcare.Activities.DetailsActivity;
import com.pop.carcare.Database.model.Reservations;
import com.pop.carcare.Database.model.Services;
import com.pop.carcare.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {

    private Context mCtx;
    private List<Reservations> ReservationList;


    public ReservationsAdapter(Context mCtx, List<Reservations> ReservationList) {
        this.mCtx = mCtx;
        this.ReservationList = ReservationList;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_card, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReservationViewHolder holder, final int position) {

        StringBuilder sb = new StringBuilder(1000);
        sb.append("Service Name : "+ReservationList.get(position).getName())
                .append("\n")
                .append("Service Price : "+ReservationList.get(position).getPrice()+" LE")
                .append("\n")
                .append("Date Of Reservation : "+ReservationList.get(position).getDate())
                .append("\n");
        holder.Nametxt.setText(sb.toString());


    }


    @Override
    public int getItemCount() {
        return ReservationList.size();
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView Nametxt;

        public ReservationViewHolder(View itemview) {
            super(itemview);
            Nametxt = itemView.findViewById(R.id.name);
        }
    }
}
