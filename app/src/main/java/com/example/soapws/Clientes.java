package com.example.soapws;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Clientes extends Fragment {


    public Clientes() {
        // Required empty public constructor
    }
    RecyclerView recyclerClientes;
    ClientesAdapter adapter;
    List<Cliente> listClientes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);
        TimbresBD bd = new TimbresBD(getContext());
        recyclerClientes = view.findViewById(R.id.recyclerClientes);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(getContext()));
        listClientes = new ArrayList<>();

        for(Cliente cliente:bd.consultaRfc()){
            listClientes.add(cliente);
        }

        adapter = new ClientesAdapter(getContext(), listClientes);
        recyclerClientes.setAdapter(adapter);
        return view;
    }

}
