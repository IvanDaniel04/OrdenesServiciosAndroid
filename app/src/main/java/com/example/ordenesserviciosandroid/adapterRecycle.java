package com.example.ordenesserviciosandroid;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adapterRecycle extends RecyclerView.Adapter<adapterRecycle.TareaViewHolder> {

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewId, textViewDescripcion, textViewUbicacion;

        public TareaViewHolder(View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewUbicacion = itemView.findViewById(R.id.textViewUbicacion);
        }
    }

}
