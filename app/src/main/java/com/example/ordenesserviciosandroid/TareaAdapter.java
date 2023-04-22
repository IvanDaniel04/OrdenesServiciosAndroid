package com.example.ordenesserviciosandroid;

import android.app.Activity;
import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> implements RecyclerView.OnItemTouchListener {

    private List<Tarea> tareas;
    OnTareaClickListener listener;
    private GestureDetector gestureDetector;


    public TareaAdapter(Context context, List<Tarea> tareas) {
        this.listener = null;
        this.tareas = tareas;
        RecyclerView recyclerView = ((Activity) context).findViewById(R.id.recyclerViewDatos);
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {

                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && listener != null) {
                    int position = recyclerView.getChildAdapterPosition(child);
                    listener.onTareaClick(position);
                    return true;
                }
                return false;
            }
        });
    }

    public interface OnTareaClickListener {
        void onTareaClick(int position);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

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

    public void setOnTareaClickListener(OnTareaClickListener listener) {
        this.listener = listener;
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
