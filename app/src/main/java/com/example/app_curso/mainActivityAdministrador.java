package com.example.app_curso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.app_curso.fragmentos_admin.ListarAdmin;
import com.example.app_curso.fragmentos_admin.PerfilAdmin;
import com.example.app_curso.fragmentos_admin.RegistroAdmin;
import com.example.app_curso.fragmentos_admin.inicioAdmin;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class mainActivityAdministrador extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);
        Toolbar toolbar = findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layaout_Admin);
        NavigationView navigationView = findViewById(R.id.nav_viewAdmin);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //fragmento por defecto
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                    new inicioAdmin()).commit();
            navigationView.setCheckedItem(R.id.inicioAdmin);
        }
    }


    //metodo para seleccionar un fragmento
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.inicioAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                        new inicioAdmin()).commit();
                break;

            case R.id.perfilAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                        new PerfilAdmin()).commit();
                break;

            case R.id.registrarAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                        new RegistroAdmin()).commit();
                break;

            case R.id.listarAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerAdmin,
                        new ListarAdmin()).commit();
                break;

            case R.id.salir:
                CerrarSesion();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void ComprobandoInicioSesion(){
        if (user != null){
            //Login Exitoso
            Toast.makeText(this, "Inicio de Sesión Correcto.", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(mainActivityAdministrador.this, MainActivity.class));
            finish();
        }

    }


    private void CerrarSesion(){
        firebaseAuth.signOut();
        startActivity(new Intent(mainActivityAdministrador.this,MainActivity.class));
        Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show();
    }

    //Metodo para inicializar algun otro metodo/funcion al momento de abrir un activity (parecido al initstate en flutter)
    @Override
    protected void onStart() {
        ComprobandoInicioSesion();
        super.onStart();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea Salir de la aplicación?")
                    .setPositiveButton("si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent= new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           finish();

                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
            builder.show();
        }


        return super.onKeyDown(keyCode, event);
    }

}