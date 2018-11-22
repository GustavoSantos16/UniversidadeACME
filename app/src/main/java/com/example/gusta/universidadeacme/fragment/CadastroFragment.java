package com.example.gusta.universidadeacme.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gusta.universidadeacme.BackableFragment;
import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.model.Tarefa;
import com.example.gusta.universidadeacme.model.TarefaDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class CadastroFragment extends BackableFragment {

    int aberto;
    EditText txt_titulo, txt_desc, txt_data;
    Button btn_cadastro, btn_lista_tarefas;
    Tarefa tarefa;
    boolean modoEdicao = false;
    Integer idTarefa = null;
    String dataSelecionada = null;

    Calendar myCalendar = Calendar.getInstance();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_cadastro, container, false);
        final Toolbar toolbar = (Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                toolbar.setTitle("Agenda");
                onBackButtonPressed();
            }
        });
        toolbar.setTitle("Cadastrar Tarefas");
        aberto = 1;


        txt_titulo = view.findViewById(R.id.txt_titulo);
        txt_desc = view.findViewById(R.id.txt_desc);
        txt_data = view.findViewById(R.id.txt_data);
        btn_cadastro = view.findViewById(R.id.btn_cadastro);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            //pegando o id para edição
           idTarefa = bundle.getInt("id");
           dataSelecionada = bundle.getString("dataSelecionada");

           if (dataSelecionada != null){
                idTarefa = null;
                txt_data.setText(dataSelecionada);
            }
        }

        if (idTarefa != null){
            modoEdicao = true;

            tarefa = TarefaDAO.getInstance().selecionarUma(getContext(),idTarefa);

            txt_titulo.setText(tarefa.getTitulo());
            txt_desc.setText(tarefa.getDescricao());
            txt_data.setText(tarefa.getData());
            btn_cadastro.setText("Editar");
        }


        //cadastrar
        btn_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tarefa t;
                if(modoEdicao){
                    t = tarefa;
                }else{
                    t = new Tarefa();
                }



                if(txt_titulo.getText().toString().isEmpty()){
                    txt_titulo.setError("Por favor, digite um titulo");
                    return;
                }
                if(txt_data.getText().toString().isEmpty()){
                    txt_data.setError("Por favor, preencha uma data");
                    return;
                }

                t.setTitulo(txt_titulo.getText().toString());
                t.setDescricao(txt_desc.getText().toString());
                t.setData(txt_data.getText().toString());

                if(modoEdicao){

                    TarefaDAO.getInstance().atualizar(getContext(), t);
                    Toast.makeText(getContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    TarefaDAO.getInstance().inserirTarefa(getContext(),t);
                    Toast.makeText(getContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new TarefasFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });


        //click listener no edittext
        txt_data.setOnClickListener(new View.OnClickListener() {

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

    //selecionar data
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



    //Atualizar o txt da data
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txt_data.setText(sdf.format(myCalendar.getTime()));
    }



    @Override
    public void onBackButtonPressed() {
        if (aberto == 1) {
            Fragment fragment = new AgendaFragment();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            getActivity().onBackPressed();
        }
    }




}
