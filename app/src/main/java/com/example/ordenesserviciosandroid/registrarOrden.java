package com.example.ordenesserviciosandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class registrarOrden extends AppCompatActivity {


    EditText areaText, descripText, nombreText, ubiText;
    Button insertar;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_orden);

        areaText = (EditText) findViewById(R.id.areaPlain);
        descripText = (EditText) findViewById(R.id.desPlain);
        nombreText = (EditText) findViewById(R.id.nombrePlain);
        ubiText = (EditText) findViewById(R.id.ubicacionPlain);
        insertar = (Button) findViewById(R.id.btnInsert);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos();
            }
        });

    }

    private void insertarDatos() {
        String area = areaText.getText().toString();
        String descripcion = descripText.getText().toString();
        String nombreSolicitante = nombreText.getText().toString();
        String ubicacion = ubiText.getText().toString();

        String url = "http://192.168.100.5:80/ordenes/insertarDatos.php?area=" + area + "&descripcion=" + descripcion + "&nombresolicitante=" + nombreSolicitante + "&ubicacion=" + ubicacion;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if (status.equals("success")) {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("PARSE_ERROR", "Error al parsear JSON: " + response, e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Toast.makeText(getApplicationContext(), "Error de conexión. Código de estado: " + statusCode, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error de conexión!", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("ERROR_VOLLEY", error.toString());
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}