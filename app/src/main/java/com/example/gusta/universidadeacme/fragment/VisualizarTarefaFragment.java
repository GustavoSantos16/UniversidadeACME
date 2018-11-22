package com.example.gusta.universidadeacme.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gusta.universidadeacme.BackableFragment;
import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.model.Tarefa;
import com.example.gusta.universidadeacme.model.TarefaDAO;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisualizarTarefaFragment extends BackableFragment {

    TextView txt_titulo_tarefa_view, txt_data_tarefa_view, txt_descricao_tarefa_view;

    TarefaDAO dao = TarefaDAO.getInstance();
    int idTarefa;
    Tarefa t;
    int aberto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_visualizar_tarefa, container, false);
        final Toolbar toolbar = (Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                onBackButtonPressed();
            }
        });
        toolbar.setTitle("Visualizar Anotação");
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_more_vert_black_24dp));



        aberto = 1;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            txt_titulo_tarefa_view = v.findViewById(R.id.txt_titulo_tarefa_view);
            txt_data_tarefa_view = v.findViewById(R.id.txt_data_tarefa_view);
            txt_descricao_tarefa_view = v.findViewById(R.id.txt_descricao_tarefa_view);

            idTarefa = bundle.getInt("id");
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        t = dao.selecionarUma(getContext(), idTarefa);

        txt_titulo_tarefa_view.setText(t.getTitulo());
        txt_data_tarefa_view.setText(t.getData());
        txt_descricao_tarefa_view.setText(t.getDescricao());
    }

    //VOLTAR A PAGINA ANTERIOR
    @Override
    public void onBackButtonPressed() {
        if (aberto == 1) {
            Fragment fragment = new TarefasFragment();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            getActivity().onBackPressed();
        }
    }


    //OPÇÕES DE EDITAR E EXCLUIR
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_options, menu) ;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.editar:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new CadastroFragment();
                Bundle bundle = new Bundle();

                //Passando o id para cadastro para efetuar a edição
                bundle.putInt("id", idTarefa);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return false;
            case R.id.excluir:

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Excluir");
                builder.setMessage("Tem certeza de que deseja excluir essa anotação ?");

                final Context context = getContext();
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TarefaDAO.getInstance().remover(getContext(),idTarefa);
                        Toast.makeText(getContext(),"Excluído com sucesso",Toast.LENGTH_LONG).show();
                        onBackButtonPressed();

                    }
                });

                builder.setNegativeButton("NÂO", null);
                builder.create().show();
                return true;

            default:
                break;
        }

        return false;
    }

}
