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
import com.example.gusta.universidadeacme.model.Noticia;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class VisualizarNoticiaFragment extends BackableFragment {

    int aberto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_visualizar_noticia, container, false);
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

            final Call<Noticia> requestCatalog =  service.ListUmaNoticia(id);

            requestCatalog.enqueue(new Callback<Noticia>() {
                @Override
                public void onResponse(Call<Noticia> call, Response<Noticia> response) {

                    Noticia resposta  = response.body();

                    TextView txt_titulo_noticia = view.findViewById(R.id.txt_titulo_noticia);
                    TextView txt_data_noticia = view.findViewById(R.id.txt_data_noticia);
                    TextView txt_desc_noticia = view.findViewById(R.id.txt_desc_noticia);

                    ImageView img_noticia = view.findViewById(R.id.img_noticia);

                    String imagem = getContext().getString(R.string.caminho_img_evento_noticia)+resposta.getFoto();
                    Picasso.get().load(imagem).into(img_noticia);

                    txt_titulo_noticia.setText(resposta.getTitulo());
                    txt_data_noticia.setText(resposta.getInicio());
                    txt_desc_noticia.setText(resposta.getDesc());

                }

                @Override
                public void onFailure(Call<Noticia> call, Throwable t) {

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
