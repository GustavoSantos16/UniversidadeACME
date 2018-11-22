package com.example.gusta.universidadeacme.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.gusta.universidadeacme.R;

import java.util.Objects;

public class AgendaFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, null);
        FloatingActionButton addTarefa = (FloatingActionButton) view.findViewById(R.id.addTarefa);
        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("Agenda");



    //Calendário
        CalendarView calendarView = view.findViewById(R.id.calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {

                int mes = month + 1;
                String dia = String.valueOf(dayOfMonth);

                if(dia.length() == 1){
                    dia = "0"+dia;
                }

                final String dataSelecionada = ""+
                        dia +"/"+mes +"/"+year;


                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //builder.setTitle("");

                // add a list
                String[] animals = {"Adicionar uma anotação"};
                builder.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        Fragment fragmentCadastro = new CadastroFragment();

                        Bundle bundle = new Bundle();

                        bundle.putString("dataSelecionada", dataSelecionada);

                        switch (which) {
                            case 0: // Add nova anotação
                                fragmentCadastro.setArguments(bundle);
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentCadastro).commit();
                                break;


                        }
                    }
                });

                builder.create().show();

                //Toast.makeText(getContext(),dataSelecionada,Toast.LENGTH_SHORT).show();

            }
        });



        //Adicionar novas tarefas
        addTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            //Instancia o fragment que você vai colocar na tela
                Fragment fragment = new CadastroFragment();

            //Faz a transação, substituindo no frame da sua MainActivity que contem os fragmentos, o antigo pelo novo fragmento que você instanciou.
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

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
        inflater.inflate(R.menu.menu_lista, menu) ;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new TarefasFragment();

        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        return false;
    }
}
