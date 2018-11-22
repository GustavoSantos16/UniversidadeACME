package com.example.gusta.universidadeacme.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.SharedPreferencesConfig;
import com.example.gusta.universidadeacme.fragment.AgendaFragment;
import com.example.gusta.universidadeacme.fragment.HomeFragment;
import com.example.gusta.universidadeacme.fragment.PerfilFragment;
import com.example.gusta.universidadeacme.fragment.QrcodeFragment;
import com.example.gusta.universidadeacme.fragment.RendimentoFragment;

import java.util.Objects;


public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private SharedPreferencesConfig preferencesConfig;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         toolbar = findViewById(R.id.toolbar);
         toolbar.setTitle("Home");


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());

        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }

        return false;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()){

            case R.id.navigation_home:
                fragment = new HomeFragment();
                toolbar.setTitle("Home");
                break;
            case R.id.navigation_agenda:
                fragment = new AgendaFragment();
                toolbar.setTitle("Agenda");
                break;
            case R.id.navigation_rendimento:
                fragment = new RendimentoFragment();
                toolbar.setTitle("Notas e Faltas");
                break;
            case R.id.navigation_qrcode:
                fragment = new QrcodeFragment();
                toolbar.setTitle("Gerar QRCode");
                break;
            case R.id.navigation_perfil:
                fragment = new PerfilFragment();
                toolbar.setTitle("Perfil");
                break;
        }

        setSupportActionBar(toolbar);
        return loadFragment(fragment);
    }
}

