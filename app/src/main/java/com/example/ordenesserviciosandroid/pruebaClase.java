package com.example.ordenesserviciosandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class pruebaClase extends AppCompatActivity implements TareaAdapter.OnTareaClickListener{
    private final Handler handler = new Handler();
    private Runnable runnableCode = null;
    private RecyclerView recyclerViewDatos;
    static TareaAdapter tareaAdapter;
    static List<Tarea> tareas = new ArrayList<>();
    RequestQueue requestQueue;
    private GestureDetectorCompat gestureDetector;
   TareaAdapter.OnTareaClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba);

        recyclerViewDatos = findViewById(R.id.recyclerViewDatos);
        recyclerViewDatos.setLayoutManager(new LinearLayoutManager(this));
        tareaAdapter = new TareaAdapter(this,tareas);
        tareaAdapter.listener = this;
        recyclerViewDatos.addOnItemTouchListener(tareaAdapter);
        recyclerViewDatos.setAdapter(tareaAdapter);
        agregarTouchListener(recyclerViewDatos);


        obtenerDatos();

        runnableCode = new Runnable() {
            @Override
            public void run() {
                obtenerDatos();
                handler.postDelayed(this, 10000);
            }
        };
        this.gestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                View child = recyclerViewDatos.findChildViewUnder(e.getX(), e.getY());
                if (child != null && listener != null) {
                    int position = recyclerViewDatos.getChildAdapterPosition(child);
                    listener.onTareaClick(position);
                    return true;
                }
                return false;
            }
        });
        handler.postDelayed(runnableCode, 10000);

    }
    @Override
    public void onTareaClick(int position) {
        // Aquí se maneja el evento de clic en una tarea
        // En este ejemplo, simplemente mostramos un mensaje con la posición de la tarea clickeada
        Tarea tareaseleccionada = tareas.get(position);
        int idTarea = tareaseleccionada.getId();
        System.out.println("ESTE ES EL ID" + idTarea);
        Toast.makeText(this, "Tarea clickeada en posición " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(pruebaClase.this, extraInfo.class);
        startActivity(intent);
    }


    public void agregarTouchListener(final RecyclerView recyclerView) {
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent( RecyclerView rv,  MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = recyclerView.getChildAdapterPosition(child);
                    tareaAdapter.listener.onTareaClick(position);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }


    private void obtenerDatos() {
        String url = "http://192.168.100.5:80/ordenes/tareas.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                tareas.clear(); // borra los datos antiguos de la lista
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String descripcion = jsonObject.getString("descripcion");
                        String ubicacion = jsonObject.getString("ubicacion");
                        tareas.add(new Tarea(id, descripcion, ubicacion));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tareaAdapter.actualizarTareas(tareas);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR_VOLLEY", error.toString());
                    }
                }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }



}












