package com.example.app_curso.fragmentos_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_curso.MainActivity;
import com.example.app_curso.R;
import com.example.app_curso.mainActivityAdministrador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import Metodos.RegistroAdministradores;

public class RegistroAdmin extends Fragment {

TextView FechaRegistro;
EditText Nombres, Apellidos, Edad, Contraseña, Correo;
Button Registrar;
FirebaseAuth auth;
ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_registro_admin, container, false);

       FechaRegistro = view.findViewById(R.id.FechaRegistro);
        Nombres = view.findViewById(R.id.Nombres);
        Apellidos = view.findViewById(R.id.Apellidos);
        Edad = view.findViewById(R.id.Edad);
        Contraseña = view.findViewById(R.id.Contraseña);
        Correo = view.findViewById(R.id.Correo);
        Registrar = view.findViewById(R.id.Registrar);
        auth = FirebaseAuth.getInstance();

        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("d 'de' MMM 'del' yyyy");
        String sFecha = fecha.format(date); //fecha a string
        FechaRegistro.setText(sFecha);



        //Evento de Registrar

        Registrar.setOnClickListener(new View.OnClickListener() {
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

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando Registro, espere por favor.");
        progressDialog.setCancelable(false);




       return view;
    }
   private void RegistroAdmins(String correo, String contrasenya){

        progressDialog.show();
        auth.createUserWithEmailAndPassword(correo, contrasenya)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Si el admin fue creado correcto
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null; //afirmar que el admin no es null

                        //Convertir a cadena los datos de los admin

                            String UID = user.getUid();
                            String correo = Correo.getText().toString();
                            String contraseñya = Contraseña.getText().toString();
                            String apellidos = Apellidos.getText().toString();
                            String edad = Edad.getText().toString();
                            String nombres = Nombres.getText().toString();
                            int EdadInt = Integer.parseInt(edad);

                            HashMap<Object, Object>Administradores = new HashMap<>();
                            Administradores.put("UID", UID);
                            Administradores.put("CORREO", correo);
                            Administradores.put("APELLIDOS",apellidos);
                            Administradores.put("EDAD",EdadInt);
                            Administradores.put("NOMBRES",nombres);
                            Administradores.put("IMAGEN","");

                            //Iniciar FirebaseDataBase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("BD ADMINS");
                            reference.child(UID).setValue(Administradores);
                            startActivity(new Intent(getActivity(), mainActivityAdministrador.class));
                            Toast.makeText(getActivity(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                            getActivity().finish();

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
   }
}