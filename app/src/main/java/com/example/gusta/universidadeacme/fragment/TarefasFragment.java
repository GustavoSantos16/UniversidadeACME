package com.example.gusta.universidadeacme.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gusta.universidadeacme.BackableFragment;
import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.adapter.TarefaAdapter;
import com.example.gusta.universidadeacme.model.Tarefa;
import com.example.gusta.universidadeacme.model.TarefaDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class TarefasFragment extends BackableFragment {
    int aberto;

    ListView list_tarefa;
    TarefaAdapter adapter;
    TarefaDAO dao;
    EditText data_consulta;
    Button btn_filtrar;


    Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tarefas, container, false);

        FloatingActionButton addTarefa = (FloatingActionButton) view.findViewById(R.id.addTarefa);
        final Toolbar toolbar = (Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("Lista de Tarefas");

        //Abrir cadastrar
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

        aberto = 1;

        dao = TarefaDAO.getInstance();

        btn_filtrar = view.findViewById(R.id.btn_filtrar);

        list_tarefa = view.findViewById(R.id.list_tarefas);
        adapter = new TarefaAdapter(getContext(), new ArrayList<Tarefa>());
        list_tarefa.setAdapter(adapter);


        list_tarefa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new VisualizarTarefaFragment();
                Bundle bundle = new Bundle();

                //Abrir fragment vizualizar evento
                Tarefa tarefa = adapter.getItem(position);
                bundle.putInt("id", tarefa.getIdTarefa());
                fragment.setArguments(bundle);

                //Abrir fragment vizualizar tarefa
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });


        data_consulta= view.findViewById(R.id.data_consulta);


    //Date picker
        data_consulta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        data_consulta.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public void onResume() {
        super.onResume();


            carregarTarefas();

            btn_filtrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filtragemPorData();

                }
            });




    }

    @Override
    public void onBackButtonPressed() {
        if (aberto == 1) {
            Fragment fragment = new CadastroFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lista, menu) ;

        menu.getItem(0).setIcon(R.drawable.ic_event_note_black_24dp);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new AgendaFragment();

        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        return false;
    }


    public void filtragemPorData(){
        if(data_consulta.getText().toString().isEmpty()){
            data_consulta.setError("Por favor, selecione uma data");
            return;
        }

        String data = data_consulta.getText().toString();

        ArrayList<Tarefa> tarefas;
        tarefas = dao.selecionarPorData(getContext(), data);
        adapter.clear();
        adapter.addAll(tarefas);

        //Se não houver cadastrado na data
        if(tarefas.size() <= 0) {
            Toast.makeText(getContext(), "Não existem anotações na data escolhida",Toast.LENGTH_SHORT).show();
            carregarTarefas();
        }

    }


    public void carregarTarefas(){
        ArrayList<Tarefa> tarefas;
        tarefas = dao.selecionarTodas(getContext());
        adapter.clear();
        adapter.addAll(tarefas);
    }





}
