package com.example.ordenesserviciosandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    //BuscarProducto("http://192.168.100.5:80/ordenes/consultas.php?id="+idEnviar.getText()+"");

    ///cambios
    Button botonIniciar, boton, botonInsertt;
    EditText eContrasena, eCorreo, enviarID, nombrRecibido;
    TextView txtSeguimiento, txtRegistrar;
    ImageView imgVolver;
    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Desactivar rotación de la ventana
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        botonIniciar = (Button) findViewById(R.id.btnIniciar);
        eContrasena = (EditText) findViewById(R.id.editContrasena);
        //  txtSeguimiento = (TextView) findViewById(R.id.textSeguimiento);
        //txtRegistrar = (TextView) findViewById(R.id.textRegistrar);
        eCorreo = (EditText) findViewById(R.id.editCorreo);

        imgVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
       /*
        enviarID = (EditText) findViewById(R.id.idEnviar);
        nombrRecibido = (EditText) findViewById(R.id.nombreRecibido);
        botonInsertt = (Button) findViewById(R.id.insButton);
           boton = (Button) findViewById(R.id.botonQ);
           botonInsertt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registrarOrden.class);
                startActivity(intent);
            }
        });
        */


        /*
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscarProducto("http://192.168.100.5:80/ordenes/consultas.php?id=" + enviarID.getText() + "");
            }
        });

         */

        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscarUsuario(eCorreo.getText().toString(), eContrasena.getText().toString());
                System.out.println(eContrasena.getText());
            }
        });


    }


    private void BuscarUsuario(String correo, String contrasena) {
        String URL = "http://192.168.100.5:80/ordenes/sesion.php?correo=" + correo + "&contrasena=" + contrasena;
        //String URL = "http://192.168.100.5:80/ordenes/sesion.php?correo=" + correo + "&contrasena=" + contrasena;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Comprobar si los datos son correctos
                            if (response.getString("id") != null) {
                                Toast.makeText(getApplicationContext(), "¡Bienvenido!", Toast.LENGTH_SHORT).show();

                                // Iniciar nueva actividad si el usuario es válido
                                    Intent intent = new Intent(Login.this, pruebaClase.class);
                                startActivity(intent);
                            } else {
                                // Si los datos son incorrectos
                                Toast.makeText(getApplicationContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        requestQueue.add(jsonObjectRequest);
    }


    private void BuscarProducto(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        nombrRecibido.setText(jsonObject.getString("nombre"));
                    } catch (JSONException jsonException) {
                        Toast.makeText(getApplicationContext(), jsonException.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                Log.e("ERROR_VOLLEY", error.toString());
                System.out.println(error.toString());
            }
        }
        );

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }




}
