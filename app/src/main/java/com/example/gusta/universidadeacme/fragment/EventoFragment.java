package com.example.gusta.universidadeacme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gusta.universidadeacme.Interface.ApiService;
import com.example.gusta.universidadeacme.model.Evento;
import com.example.gusta.universidadeacme.adapter.EventoAdapter;
import com.example.gusta.universidadeacme.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  EventoFragment extends Fragment {
    ListView listViewEvento;

    private static final String TAG = "BOA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evento, container, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiService service = retrofit.create(ApiService.class);

        Call<List<Evento>> requestCatalog =  service.ListEventos();


        listViewEvento = view.findViewById(R.id.listViewEvento);

        final EventoAdapter adapter = new EventoAdapter(getActivity().getApplicationContext());


        requestCatalog.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if (response.isSuccessful()){
                    Log.e(TAG,"evento funcionou" + response.code());

                    List<Evento> catalogo = response.body();

                    adapter.clear();

                    adapter.addAll(catalogo);
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Log.e(TAG, "ERRO;");
            }
        });


        listViewEvento.setAdapter(adapter);

        //Clique da lista para abrir o visualizar
        listViewEvento.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                Evento evento = adapter.getItem(position);

                //Abrir fragment vizualizar evento
                Fragment fragment = new VisualizarEventoFragment();
                Bundle bundle = new Bundle();

                bundle.putInt("id", evento.getIdEvento());
                fragment.setArguments(bundle);


                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

            }

        });
        return view;
    }


}
