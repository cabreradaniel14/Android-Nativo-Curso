package CategoriasAdmin.PeliculasAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_curso.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AgregarPelicula extends AppCompatActivity {

    TextView VistaPeliculas;
    EditText NombrePelicula;
    ImageView ImagenAgregarPeliculas;
    Button PublicarPeliculas;

    String RutaDeAlmacenamiento = "Pelicula_Subida";
    String RutaDeBaseDeDatos = "Peliculas";
    Uri RutaArchivoUri;

    StorageReference mStorageReference;
    DatabaseReference DatabaseReference;

    ProgressDialog progressDialog;
   
    int CODIGO_SOLICITUD_IMAGEN = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pelicula);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Publicar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        VistaPeliculas = findViewById(R.id.VistaPeliculas);
        NombrePelicula = findViewById(R.id.NombresPeliculas);
        ImagenAgregarPeliculas = findViewById(R.id.imagenPelicula);
        PublicarPeliculas = findViewById(R.id.btnPublicar);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        DatabaseReference = FirebaseDatabase.getInstance().getReference(RutaDeBaseDeDatos);
        progressDialog = new ProgressDialog(AgregarPelicula.this);

        ImagenAgregarPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"),CODIGO_SOLICITUD_IMAGEN);

            }
        });

        PublicarPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubirImagen();
            }
        });

    }

    private void SubirImagen() {
        if(RutaArchivoUri!=null){
            progressDialog.setTitle("Espere por favor");
            progressDialog.setMessage("Subiendo imagen Pelicula");
            progressDialog.show();
            progressDialog.setCancelable(false);
            StorageReference storageReference2 = mStorageReference.child(RutaDeAlmacenamiento+System.currentTimeMillis()+"."+ObtenerExtensionDeArchivo(RutaArchivoUri));
            storageReference2.putFile(RutaArchivoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloaduRI = uriTask.getResult();

                            String mNombre = NombrePelicula.getText().toString();
                            String mVista = VistaPeliculas.getText().toString();
                            int VISTA = Integer.parseInt(mVista);

                            Peliculas peliculas = new Peliculas(downloaduRI.toString(),mNombre,VISTA);
                            String ID_IMAGEN = DatabaseReference.push().getKey();
                            DatabaseReference.child(ID_IMAGEN).setValue(peliculas);

                            progressDialog.dismiss();
                            Toast.makeText(AgregarPelicula.this, "Subido correctamente", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(AgregarPelicula.this,PeliculasAdmin.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AgregarPelicula.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressDialog.setTitle("Publicando");
                    progressDialog.setCancelable(false);
                }
            });
        }
        else{
            Toast.makeText(this, "Debe Seleccionar una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private String ObtenerExtensionDeArchivo(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_SOLICITUD_IMAGEN
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            RutaArchivoUri = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),RutaArchivoUri);
                ImagenAgregarPeliculas.setImageBitmap(bitmap);

            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}