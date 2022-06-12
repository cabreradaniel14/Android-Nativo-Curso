package com.example.app_curso.FragmentosClientes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.app_curso.ActivityInicioSesion;
import com.example.app_curso.R;

public class AcerDeCliente extends Fragment {

Button Acceder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_acer_de_cliente, container, false);

        Acceder = view.findViewById(R.id.Acceder);
        Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityInicioSesion.class));

            }
        });

        return view;
    }
}