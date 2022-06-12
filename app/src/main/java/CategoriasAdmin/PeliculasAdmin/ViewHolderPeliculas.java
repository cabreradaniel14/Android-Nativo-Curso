package CategoriasAdmin.PeliculasAdmin;



import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_curso.R;
import com.squareup.picasso.Picasso;

public class ViewHolderPeliculas extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolderPeliculas.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position); //Admin presiona normal el item
        void onItemLongClick(View view, int position);//Admin mantiene presionado el item
    }

    //Metodo para poder presionar o mantener presionado un item
    public void setOnClickListener(ViewHolderPeliculas.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public ViewHolderPeliculas(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getBindingAdapterPosition());
                return false;
            }
        });


    }
    public void SeteoPeliculas(Context context, String nombre, int vista, String imagen){
        ImageView imagenPelicula;
        TextView NombreImagenPeliculas;
        TextView VistaPeliculaItem;

        //Conexion con el item
        imagenPelicula = mView.findViewById(R.id.imagenPelicula);
        NombreImagenPeliculas = mView.findViewById(R.id.NombreImagenPeliculas);
        VistaPeliculaItem = mView.findViewById(R.id.VistaPeliculas);

        NombreImagenPeliculas.setText(nombre);

        //Convertir a String el parametro vista
        String VistaString = String.valueOf(vista);
        VistaPeliculaItem.setText(VistaString);

        //Controlar posibles errores
        try{
            //Si la imagen fue traida con exito
            Picasso.get().load(imagen).into(imagenPelicula);
        }
        catch (Exception e){
            //Si la imagen no fue traida con exito
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
