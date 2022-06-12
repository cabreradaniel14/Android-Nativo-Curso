package Metodos;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.app_curso.fragmentos_admin.RegistroAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistroAdministradores {
    FirebaseAuth auth;

    public void registroAdministrador(String correo, String contraseña, ProgressDialog progressDialog, RegistroAdmin context){
        auth.createUserWithEmailAndPassword(correo,contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                        }
                    }
                });


    }
}
