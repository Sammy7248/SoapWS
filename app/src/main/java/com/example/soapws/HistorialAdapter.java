package com.example.soapws;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {


    Context mycontext;
    List<Historial> historialList;

    public HistorialAdapter(Context mycontext, List<Historial> historialList) {
        this.mycontext = mycontext;
        this.historialList = historialList;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.card_historial, null);
        HistorialViewHolder holder = new HistorialViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {

        Historial hist = historialList.get(position);
        holder.fecha.setText(hist.date);
        holder.timbres.setText(String.valueOf(hist.timbresAsignados));

    }

    @Override
    public int getItemCount() {
        return historialList.size();
    }

    class HistorialViewHolder extends RecyclerView.ViewHolder{

        TextView fecha, timbres;
        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.date);
            timbres = itemView.findViewById(R.id.timbres);
        }
    }
}
