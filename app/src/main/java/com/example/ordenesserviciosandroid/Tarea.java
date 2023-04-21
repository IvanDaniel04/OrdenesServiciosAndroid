package com.example.ordenesserviciosandroid;

public class Tarea {
    private int id;
    private String descripcion;
    private String ubicacion;

    public Tarea(int id, String descripcion, String ubicacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
