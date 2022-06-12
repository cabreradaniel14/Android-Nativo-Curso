package com.example.app_curso.fragmentos_admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.app_curso.R;

import CategoriasAdmin.MusicaAdmin.MusicaAdmin;
import CategoriasAdmin.PeliculasAdmin.PeliculasAdmin;
import CategoriasAdmin.SeriesAdmin.SeriesAdmin;
import CategoriasAdmin.VideoJuegosAdmin.VideoJuegosAdmin;


public class inicioAdmin extends Fragment {

    Button peliculas, series, musica, juegos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio_admin, container, false);

        peliculas = view.findViewById(R.id.peliculas);
        series = view.findViewById(R.id.series);
        musica = view.findViewById(R.id.musica);
        juegos = view.findViewById(R.id.videojuegos);

        peliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PeliculasAdmin.class));
            }
        });

        series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SeriesAdmin.class));
            }
        });

        musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MusicaAdmin.class));
            }
        });

        juegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), VideoJuegosAdmin.class));
            }
        });
        return view;
    }
}