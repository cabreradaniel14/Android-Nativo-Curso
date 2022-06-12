package com.example.app_curso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityInicioSesion extends AppCompatActivity {
    EditText Correo, Contraseña;
    Button Acceder;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        ActionBar actionBar = getSupportActionBar(); //Creamos  el actionbar
        actionBar.setTitle("Inicio Sesión"); // Se le da un titulo
        actionBar.setDisplayHomeAsUpEnabled(true);//Boton de retroceso
        actionBar.setDisplayShowHomeEnabled(true);

        Correo = findViewById(R.id.Correo);
        Contraseña = findViewById(R.id.Contraseña);
        Acceder = findViewById(R.id.Acceder);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(ActivityInicioSesion.this);
        progressDialog.setMessage("Iniciando Sesión, espere por favor.");
        progressDialog.setCancelable(false);

        Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = Correo.getText().toString();
                String password = Contraseña.getText().toString();

                //Validación del correo
                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    Correo.setError("Correo inválido");
                    Correo.setFocusable(true);
                } else if (password.length()<6){
                    Contraseña.setError("Por favor, ingrese seis dígitos o más.");
                    Contraseña.setFocusable(true);
                }else{
                    RegistroAdmins(correo,password);
                }
            }
        });
    }

    private void RegistroAdmins(String correo, String password) {
        progressDialog.show();
        progressDialog.setCancelable(false);
        firebaseAuth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(ActivityInicioSesion.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Loggeo exitoso
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(ActivityInicioSesion.this,mainActivityAdministrador.class));
                            Toast.makeText(ActivityInicioSesion.this, "Bienvenido(a)"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            progressDialog.dismiss();
                            UsuarioInvalido();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                UsuarioInvalido();
            }
        });
    }

    private void UsuarioInvalido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInicioSesion.this);
        builder.setCancelable(false);
        builder.setTitle("ERROR AL INICIAR SESIÓN");
        builder.setMessage("Verifique si el correo o contraseña son correctos.")
        .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}