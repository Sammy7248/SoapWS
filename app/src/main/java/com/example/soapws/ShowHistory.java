package com.example.soapws;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class ShowHistory extends Fragment {


    public ShowHistory() {
        // Required empty public constructor
    }

    RecyclerView recyclerHistorial;
    HistorialAdapter adapter;
    List<Historial> historialList;
    SwipeRefreshLayout refreshHistory;
    TimbresBD bd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_history, container, false);
        recyclerHistorial = view.findViewById(R.id.recyclerHistorial);
        recyclerHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
        historialList = new ArrayList<>();

        bd = new TimbresBD(getContext());
        for(Historial hi:bd.consultar()){
            if(!historialList.contains(hi)){
                historialList.add(hi);
            }
        }


        refreshHistory = view.findViewById(R.id.refHistory);

        refreshHistory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                historialList.clear();
                List<Historial> listChanges = new ArrayList<>();
                for(Historial hiN:bd.consultar()){
                    if(!historialList.contains(hiN)){
                        historialList.add(hiN);
                    }
                }

                /*if(listChanges.size() > historialList.size()){
                    for(Historial histNew:listChanges){
                        if(!historialList.contains(histNew)){
                            historialList.add(histNew);
                        }
                    }
                }*/

                adapter.notifyDataSetChanged();
                refreshHistory.setRefreshing(false);

            }
        });

        adapter = new HistorialAdapter(getContext(), historialList);
        recyclerHistorial.setAdapter(adapter);

        return view;
    }

}
