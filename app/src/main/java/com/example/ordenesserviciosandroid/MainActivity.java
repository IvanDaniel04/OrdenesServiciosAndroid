package com.example.ordenesserviciosandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    CardView crdSeguimiento, crdNueva, crdIniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crdIniciar = findViewById(R.id.crdinciar);
        crdNueva = findViewById(R.id.crdnueva);
        crdSeguimiento = findViewById(R.id.crdsiguiente);


        crdIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
        crdSeguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, seguimientOrden.class);
                startActivity(intent);
            }
        });
        crdNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registrarOrden.class);
                startActivity(intent);
            }
        });
    }


}
