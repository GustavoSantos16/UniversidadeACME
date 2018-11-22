package com.example.gusta.universidadeacme.activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.SharedPreferencesConfig;
import com.example.gusta.universidadeacme.model.Aluno;
import com.example.gusta.universidadeacme.Interface.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText txt_matricula, txt_senha;
    int matricula = 0;
    String senha;

    private SharedPreferencesConfig preferencesConfig;
    private AlertDialog alertDialog;

    private static final String TAG = "ERRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_matricula = findViewById(R.id.txt_matricula);
        txt_senha = findViewById(R.id.txt_senha);

        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        if (preferencesConfig.readLoginStatus()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void logar(View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiService service = retrofit.create(ApiService.class);

        if(txt_matricula.getText().toString().isEmpty()){
            txt_matricula.setError("Campo obrigatório");
            return;
        }

        if(txt_senha.getText().toString().isEmpty()){
            txt_senha.setError("Campo obrigatório");
            return;
        }

        matricula = Integer.parseInt(txt_matricula.getText().toString());


        //Try_catch para matricula
        senha = txt_senha.getText().toString();

        final Call<Aluno> requestCatalog =  service.Logar(matricula, senha);

        requestCatalog.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                Aluno resposta = response.body();

                preferencesConfig.writeUsuarioNome(resposta.getNome());
                preferencesConfig.writeUsuarioMatricula(String.valueOf(resposta.getMatricula()));
                preferencesConfig.writeLoginStatus(true);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();


            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Log.e(TAG,"Erro ao logar");

                Toast toast = new Toast(getApplicationContext());
                toast.makeText(getApplicationContext(),"Erro: Usuario ou senha invalido!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
