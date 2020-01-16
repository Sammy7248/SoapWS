package com.example.soapws;


import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Asignacion extends Fragment {

    //NOMBRE DEL METODO = OPERATION
    //NAMESPACE = TARGET NAMESPACE
    EditText username, password, taxpayerId, creditos;
    static SoapObject result;
    String mensaje = "";
    Button enviar;
    TextView showResp, mostReg;
    int resp_total_creditos = 0;
    String resp_message = "";
    String username_val, password_val, taxpayer_id_val;
    List<String> listRfc;
    AutoCompleteTextView autocompleteRfc;
    String cred_tot;


    public Asignacion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asignacion, container, false);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        taxpayerId = view.findViewById(R.id.autocompleteRfc);
        creditos = view.findViewById(R.id.creditos);
        enviar = view.findViewById(R.id.enviar);
        mostReg = view.findViewById(R.id.mostReg);
        autocompleteRfc = view.findViewById(R.id.autocompleteRfc);
        taxpayer_id_val = taxpayerId.getText().toString();
        username_val = username.getText().toString();


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segundoPlano plano = new segundoPlano();
                plano.execute();
                //creditos.setText("");
            }
        });

        listRfc = new ArrayList<>();

        TimbresBD bd = new TimbresBD(getContext());
        for(Cliente cliente: bd.consultaRfc()){
            if(!listRfc.contains(cliente.Rfc)){
                listRfc.add(cliente.Rfc);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,listRfc);
        autocompleteRfc.setAdapter(adapter);


        return view;
    }

    private void convertir() throws Exception{
        String SOAP_aCTION = "assign";
        String METHOD_NAME = "assign";
        String NAMESPACE = "http://facturacion.finkok.com/registration";
        String URL = "https://demo-facturacion.finkok.com/servicios/soap/registration.wsdl";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("username", username.getText().toString());
        request.addProperty("password", password.getText().toString());
        request.addProperty("taxpayer_id",taxpayerId.getText().toString());
        request.addProperty("credit",Integer.parseInt(creditos.getText().toString()));

        SoapSerializationEnvelope envelopre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelopre.dotNet = true;
        envelopre.setOutputSoapObject(request);

        HttpTransportSE transport = new HttpTransportSE(URL);
        transport.call(SOAP_aCTION, envelopre);
        //EL RESULT TRAE CONSIGO EL RESPONSE DE LA PETICION
        result = (SoapObject) envelopre.getResponse();
        mensaje = result.getProperty(0).toString();
        cred_tot = result.getProperty(1).toString();

        //success = (boolean) result.getProperty(0);

    }

    private class segundoPlano extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                convertir();
            }
            catch (Exception e){
                mensaje = "ERROR: " + e.getMessage();
                Log.i("Excepcion", mensaje);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Toast.makeText(getContext(), String.valueOf(success).toString(), Toast.LENGTH_LONG).show();
            try{
                boolean success = Boolean.parseBoolean(mensaje);
                if(success == true){
                    final TimbresBD bd = new TimbresBD(getContext());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                    Date date = new Date();
                    final String fecha = dateFormat.format(date);

                    Historial hist = new Historial(username.getText().toString(), taxpayerId.getText().toString(),Integer.parseInt(creditos.getText().toString()),fecha.toString());
                    bd.agregarRegistro(hist);


                    bd.agregarRfcCliente(taxpayerId.getText().toString(), username.getText().toString());
                    Toast.makeText(getContext(), "Exito, el RFC "+ taxpayerId.getText().toString() + " cuenta con un total de "+ cred_tot + " creditos", Toast.LENGTH_LONG).show();
                    taxpayerId.setText("");
                    creditos.setText("");

                }
                else{
                    Toast.makeText(getContext(), "Error...", Toast.LENGTH_LONG).show();
                    taxpayerId.setText("");
                    creditos.setText("");
                    username.setText("");
                    password.setText("");
                }
            }catch (Exception e){
                Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_LONG).show();
            }

        }
    }

}
