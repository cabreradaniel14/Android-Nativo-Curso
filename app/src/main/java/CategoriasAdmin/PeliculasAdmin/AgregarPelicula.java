package CategoriasAdmin.PeliculasAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_curso.R;



public class AgregarPelicula extends AppCompatActivity {

    TextView VistaPeliculas;
    EditText NombrePelicula;
    ImageView ImagenAgregarPeliculas;
    Button PublicarPeliculas;

    String RutaDeAlmacenamiento = "Pelicula_Subida";
    String RutaDeBaseDeDatos = "Peliculas";
    Uri RutaArchivoUri;

   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pelicula);
    }
}