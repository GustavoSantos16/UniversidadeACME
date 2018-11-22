package com.example.gusta.universidadeacme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gusta.universidadeacme.Interface.ApiService;
import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.adapter.NoticiaAdapter;
import com.example.gusta.universidadeacme.model.Noticia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoticiaFragment extends Fragment {

    ListView listViewNoticia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_noticia, container, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiService service = retrofit.create(ApiService.class);

        Call<List<Noticia>> requestCatalog =  service.ListNoticias();



        listViewNoticia = view.findViewById(R.id.listViewNoticia);

        final NoticiaAdapter adapter = new NoticiaAdapter(getActivity().getApplicationContext());


        requestCatalog.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                if (response.isSuccessful()){

                    List<Noticia> catalogo = response.body();

                    adapter.clear();

                    adapter.addAll(catalogo);

                }
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {

            }
        });


        listViewNoticia.setAdapter(adapter);



        listViewNoticia.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                Noticia noticia = adapter.getItem(position);

                Fragment fragment = new VisualizarNoticiaFragment();
                Bundle bundle = new Bundle();

                bundle.putInt("id", noticia.getIdNoticia());
                fragment.setArguments(bundle);


                //Abrir fragment vizualizar noticia
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

            }

        });





        return view;



    }




}
