package com.example.gusta.universidadeacme.Interface;

import com.example.gusta.universidadeacme.model.Aluno;
import com.example.gusta.universidadeacme.model.Evento;
import com.example.gusta.universidadeacme.model.Noticia;
import com.example.gusta.universidadeacme.model.Rendimento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    public static final String BASE_URL = "http://10.0.2.2/INF4T/alcateck/api/";

    @GET("selecionarNoticias.php")
    Call <List<Noticia>>ListNoticias();


    @GET("selecionarNoticias.php")
    Call<Noticia>ListUmaNoticia( @Query("id") int id);


    @GET("selecionarEventos.php")
    Call<List<Evento>>ListEventos();

    @GET("selecionarEventos.php")
    Call<Evento>ListUmEvento( @Query("id") int id);

    @GET("selecionarRendimento.php")
    Call<List<Rendimento>>ListRendimento(@Query("matricula")int matricula);


    //Acesso a api para logar

    @GET("login.php")
    Call<Aluno>Logar(@Query("matricula")int matricula, @Query("senha") String senha);

}
