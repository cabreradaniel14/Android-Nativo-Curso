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

import com.example.app_curso.FragmentosClientes.AcerDeCliente;
import com.example.app_curso.FragmentosClientes.CompartirCliente;
import com.example.app_curso.FragmentosClientes.InicioCliente;
import com.example.app_curso.fragmentos_admin.ListarAdmin;
import com.example.app_curso.fragmentos_admin.PerfilAdmin;
import com.example.app_curso.fragmentos_admin.RegistroAdmin;
import com.example.app_curso.fragmentos_admin.inicioAdmin;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarCliente);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layaout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //fragmento por defecto
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new InicioCliente()).commit();
            navigationView.setCheckedItem(R.id.InicioCliente);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.InicioCliente:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InicioCliente()).commit();
                break;

            case R.id.Acerde:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AcerDeCliente()).commit();
                break;

            case R.id.Compartir:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CompartirCliente()).commit();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
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