package com.example.ordenesserviciosandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class extraInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extrainfoxml);

        // Obtener el valor del extra "id" del Intent
        int id = getIntent().getIntExtra("id", 0);
        System.out.println(id);

        // Usar el valor del id en una consulta o para mostrar información en la actividad
        // ...
    }
}

