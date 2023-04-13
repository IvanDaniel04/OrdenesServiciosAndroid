package com.example.ordenesserviciosandroid;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mysql.cj.result.Row;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class pruebaClase extends AppCompatActivity {
    private final Handler handler = new Handler();
    private Runnable runnableCode = null;
    private TableLayout tableLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba); // Establecer el layout de la actividad
        tableLayout = findViewById(R.id.tablaDatos);

        obtenerDatos();



        runnableCode = new Runnable() {
            @Override
            public void run() {
                obtenerDatos();
                handler.postDelayed(this, 10000);
            }
        };

        // Iniciar la actualización automática
        handler.postDelayed(runnableCode, 10000);

    }
    RequestQueue requestQueue;

    private void obtenerDatos() {
        String url = "http://192.168.100.5:80/ordenes/tareas.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar los datos devueltos en el array JSON
                        tableLayout.removeAllViews();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String descripcion = jsonObject.getString("descripcion");
                                String ubicacion = jsonObject.getString("ubicacion");
                                agregarFilaTabla(id, descripcion, ubicacion);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la conexión
                        Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR_VOLLEY", error.toString());
                    }
                }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    private void agregarFilaTabla(String idValor, String descripcionValor, String ubicacionValor) {
        TableLayout tableLayout = findViewById(R.id.tablaDatos);

        // Crear una nueva fila
        TableRow fila = new TableRow(this);
        fila.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // Crear los textviews para cada valor y agregarlos a la fila
        TextView textViewId = new TextView(this);
        textViewId.setText(idValor);
        textViewId.setLayoutParams(new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f));
        textViewId.setPadding(5, 5, 5, 5);
        fila.addView(textViewId);

        TextView textViewDescripcion = new TextView(this);
        textViewDescripcion.setText(descripcionValor);
        textViewDescripcion.setLayoutParams(new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f));
        textViewDescripcion.setPadding(5, 5, 5, 5);
        fila.addView(textViewDescripcion);

        TextView textViewUbicacion = new TextView(this);
        textViewUbicacion.setText(ubicacionValor);
        textViewUbicacion.setLayoutParams(new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f));
        textViewUbicacion.setPadding(5, 5, 5, 5);
        fila.addView(textViewUbicacion);

        // Agregar la fila a la tabla
        tableLayout.addView(fila, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }


}











