package com.example.ordenesserviciosandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

    ImageView imgVolveri;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_orden);

        areaText = (EditText) findViewById(R.id.areaPlain);
        descripText = (EditText) findViewById(R.id.desPlain);
        nombreText = (EditText) findViewById(R.id.nombrePlain);
        ubiText = (EditText) findViewById(R.id.ubicacionPlain);
        imgVolveri = (ImageView) findViewById(R.id.imgVolIni);

        imgVolveri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent(registrarOrden.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void insertarDatos(View view) {
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

                                LayoutInflater inflater = (LayoutInflater)
                                        getSystemService(LAYOUT_INFLATER_SERVICE);
                                View popupView = inflater.inflate(R.layout.popup, null);

                                // create the popup window
                                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                boolean focusable = true; // lets taps outside the popup also dismiss it
                                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                                // show the popup window
                                // which view you pass in doesn't matter, it is only used for the window tolken
                                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                                // dismiss the popup window when touched
                                popupView.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        popupWindow.dismiss();
                                        return true;
                                    }
                                });

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