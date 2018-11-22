package com.example.gusta.universidadeacme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gusta.universidadeacme.activity.LoginActivity;
import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.SharedPreferencesConfig;

public class PerfilFragment extends Fragment {

    private SharedPreferencesConfig preferencesConfig;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_perfil, null);
        preferencesConfig = new SharedPreferencesConfig(getContext());

        TextView txtMatricula = view.findViewById(R.id.txt_matricula_usuario);
        TextView txtUsuario = view.findViewById(R.id.txt_nome_usuario);

        txtMatricula.setText(preferencesConfig.readUsuarioMatricula());
        txtUsuario.setText(preferencesConfig.readUsuarioNome());


        Button logout = view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencao = new Intent(getContext(), LoginActivity.class);

                preferencesConfig.writeLoginStatus(false);
                startActivity(intencao);

            }
        });
        return view;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.versao, menu) ;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getContext(),"Numero da versão: 1.3.0.18",Toast.LENGTH_LONG).show();

        return false;
    }
}
