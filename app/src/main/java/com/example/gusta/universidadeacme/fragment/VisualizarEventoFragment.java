package com.example.gusta.universidadeacme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gusta.universidadeacme.BackableFragment;
import com.example.gusta.universidadeacme.Interface.ApiService;
import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.model.Evento;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class VisualizarEventoFragment extends BackableFragment{

    int aberto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_visualizar_evento, container, false);
        final Toolbar toolbar = (Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                onBackButtonPressed();
            }
        });
        toolbar.setTitle("Eventos / Noticias");

        aberto = 1;
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            int id = bundle.getInt("id");

            Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            ApiService service = retrofit.create(ApiService.class);

            final Call<Evento> requestCatalog =  service.ListUmEvento(id);

            requestCatalog.enqueue(new Callback<Evento>() {
                @Override
                public void onResponse(Call<Evento> call, Response<Evento> response) {
                    Evento resposta = response.body();

                    TextView txt_titulo_evento = view.findViewById(R.id.txt_titulo_evento);
                    TextView txt_inicio_evento = view.findViewById(R.id.txt_inicio_evento);
                    TextView txt_desc_evento = view.findViewById(R.id.txt_desc_evento);

                    ImageView img_evento = view.findViewById(R.id.img_evento);

                    String imagem = getContext().getString(R.string.caminho_img_evento_noticia)+resposta.getFoto();
                    Picasso.get().load(imagem).into(img_evento);

                    txt_titulo_evento.setText(resposta.getNome());
                    txt_inicio_evento.setText(resposta.getInicio());
                    txt_desc_evento.setText(resposta.getDesc());
                }

                @Override
                public void onFailure(Call<Evento> call, Throwable t) {

                }
            });
        }
        return view;
    }

    @Override
    public void onBackButtonPressed() {
        if (aberto == 1) {
            Fragment fragment = new HomeFragment();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            getActivity().onBackPressed();
        }
    }
}
