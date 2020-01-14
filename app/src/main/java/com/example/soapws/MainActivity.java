package com.example.soapws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    //NOMBRE DEL METODO = OPERATION
    //NAMESPACE = TARGET NAMESPACE
    EditText username, password, taxpayerId, creditos;
    SoapPrimitive result;
    String mensaje = "";
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        taxpayerId = findViewById(R.id.taxpayerId);
        creditos = findViewById(R.id.creditos);
        enviar = findViewById(R.id.enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segundoPlano plano = new segundoPlano();
                plano.execute();
            }
        });
    }

    private void convertir() {
        try {
            String SOAP_aCTION = "assign";
            String METHOD_NAME = "assign";
            String NAMESPACE = "";
            String URL = "";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username","");
            request.addProperty("password", "");
            request.addProperty("taxpayer_id","");
            request.addProperty("credit",Integer.parseInt(creditos.getText().toString()));

            SoapSerializationEnvelope envelopre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelopre.dotNet = true;
            envelopre.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_aCTION, envelopre);
            result = (SoapPrimitive) envelopre.getResponse();
            mensaje = "OK";
        }
        catch (Exception e){
            mensaje = "ERROR" + e.getMessage();
        }
    }

    private class segundoPlano extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            convertir();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        }
    }
}
