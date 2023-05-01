package com.example.ordenesserviciosandroid;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class extraInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extrainfoxml);

        Spinner spinner = (Spinner) findViewById(R.id.spinners);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.Estatus, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Obtener el valor del extra "id" del Intent
        int id = getIntent().getIntExtra("id", 0);
        System.out.println(id);

        // Usar el valor del id en una consulta o para mostrar información en la actividad
        // ...
    }
}

