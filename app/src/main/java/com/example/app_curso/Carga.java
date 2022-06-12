package com.example.app_curso;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

public class Carga extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga);




        final int DURACION = 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //EJECUCION DE CODIGO
                Intent intent = new Intent(Carga.this, mainActivityAdministrador.class);

                startActivity(intent);
                finish();

            }
        }, DURACION);



    }



}