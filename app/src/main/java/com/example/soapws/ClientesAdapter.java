package com.example.soapws;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClienteHolder> {

    Context myContext;
    List<Cliente> listCliente;

    public ClientesAdapter(Context myContext, List<Cliente> listCliente) {
        this.myContext = myContext;
        this.listCliente = listCliente;
    }

    @NonNull
    @Override
    public ClienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.card_cliente,null);
        ClienteHolder holder = new ClienteHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteHolder holder, int position) {
        Cliente cliente = listCliente.get(position);
        holder.rfc.setText(cliente.Rfc);

    }

    @Override
    public int getItemCount() {
        return listCliente.size();
    }

    class ClienteHolder extends RecyclerView.ViewHolder{

        TextView rfc;
        public ClienteHolder(@NonNull View itemView) {
            super(itemView);
            rfc = itemView.findViewById(R.id.rfc);

        }
    }
}
