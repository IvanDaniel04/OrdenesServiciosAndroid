package com.example.ordenesserviciosandroid;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



    public class adapterRecycle extends RecyclerView.Adapter<adapterRecycle.TareaViewHolder> {

        private final List<Tarea> tareas;

        public adapterRecycle(List<Tarea> tareas) {
            this.tareas = tareas;
        }

        @NonNull
        @Override
        public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclestyle, parent, false);
            return new TareaViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
            // Obtener la tarea correspondiente a esta posición en el RecyclerView
            Tarea tarea = tareas.get(position);

            // Mostrar los datos de la tarea en los TextView del ViewHolder
            holder.textViewId.setText(String.valueOf(tarea.getId()));
            holder.textViewDescripcion.setText(tarea.getDescripcion());
            holder.textViewUbicacion.setText(tarea.getUbicacion());

            // Agregar un OnClickListener al TextView de la descripción
            holder.textViewDescripcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("RecyclerView", "Se detectó un clic en la posición " + holder.getAdapterPosition());
                    Log.d("RecyclerView", "onClickListener");
                    // Obtener la tarea correspondiente a esta posición en el RecyclerView
                    Tarea tarea = tareas.get(holder.getAdapterPosition());

                    // Obtener el valor del campo "id" de la tarea
                    int id = tarea.getId();

                    // Crear un Intent para abrir la nueva actividad
                    Intent intent = new Intent(v.getContext(), extraInfo.class);

                    // Pasar el valor del campo "id" como un parámetro extra en el Intent
                    intent.putExtra("id", id);

                    // Iniciar la nueva actividad
                    v.getContext().startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return tareas.size();
        }

        public static class TareaViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewId, textViewDescripcion, textViewUbicacion;

            public TareaViewHolder(View itemView) {
                super(itemView);
                textViewId = itemView.findViewById(R.id.textViewId);
                textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
                textViewUbicacion = itemView.findViewById(R.id.textViewUbicacion);
            }
        }
    }

