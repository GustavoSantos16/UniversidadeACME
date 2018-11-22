package com.example.gusta.universidadeacme.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gusta.universidadeacme.Interface.ApiService;
import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.SharedPreferencesConfig;
import com.example.gusta.universidadeacme.adapter.RendimentoAdapter;
import com.example.gusta.universidadeacme.model.Rendimento;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RendimentoFragment extends Fragment {

    ListView list_view_rendimento;

    private SharedPreferencesConfig preferencesConfig;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_rendimento, null);

        preferencesConfig = new SharedPreferencesConfig(getContext());
        final String matricula = preferencesConfig.readUsuarioMatricula();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService service = retrofit.create(ApiService.class);

        list_view_rendimento = v.findViewById(R.id.list_view_rendimento);

        final RendimentoAdapter adapter = new RendimentoAdapter(getActivity().getApplicationContext());

        final Call<List<Rendimento>> requestCatalog =  service.ListRendimento(Integer.parseInt(matricula));


        requestCatalog.enqueue(new Callback<List<Rendimento>>() {
            @Override
            public void onResponse(Call<List<Rendimento>> call, Response<List<Rendimento>> response) {
                List<Rendimento> catalogo = response.body();

                adapter.clear();
                adapter.addAll(catalogo);

            }

            @Override
            public void onFailure(Call<List<Rendimento>> call, Throwable t) {

            }
        });

        list_view_rendimento.setAdapter(adapter);

        return v;
    }
}
