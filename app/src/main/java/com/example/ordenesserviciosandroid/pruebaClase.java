package com.example.ordenesserviciosandroid;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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


public class pruebaClase extends AppCompatActivity {
    private final Handler handler = new Handler();
    private Runnable runnableCode = null;
    private RecyclerView recyclerViewDatos;
    private TareaAdapter tareaAdapter;
    static List<Tarea> tareas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba);

        recyclerViewDatos = findViewById(R.id.recyclerViewDatos);
        recyclerViewDatos.setLayoutManager(new LinearLayoutManager(this));
        tareaAdapter = new TareaAdapter();
        recyclerViewDatos.setAdapter(tareaAdapter);
        adapterRecycle adapter = new adapterRecycle(tareas);

        obtenerDatos();

        runnableCode = new Runnable() {
            @Override
            public void run() {
                obtenerDatos();
                handler.postDelayed(this, 10000);
            }
        };

        handler.postDelayed(runnableCode, 10000);
    }

    RequestQueue requestQueue;

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


    private class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {


        @Override
        public TareaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclestyle, parent, false);
            return new TareaViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TareaViewHolder holder, int position) {
            Tarea tareaActual = tareas.get(position);
            holder.textViewId.setText(String.valueOf(tareaActual.getId()));
            holder.textViewDescripcion.setText(tareaActual.getDescripcion());
            holder.textViewUbicacion.setText(tareaActual.getUbicacion());
        }

        @Override
        public int getItemCount() {
            return tareas.size();
        }

        public void actualizarTareas(List<Tarea> nuevasTareas) {
            tareas = nuevasTareas;
            notifyDataSetChanged();
        }


        class TareaViewHolder extends RecyclerView.ViewHolder {
            private TextView textViewId;

            private TextView textViewDescripcion;
            private TextView textViewUbicacion;

            public TareaViewHolder(View itemView) {
                super(itemView);
                textViewId = itemView.findViewById(R.id.textViewId);
                textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
                textViewUbicacion = itemView.findViewById(R.id.textViewUbicacion);
            }
        }
    }
}












