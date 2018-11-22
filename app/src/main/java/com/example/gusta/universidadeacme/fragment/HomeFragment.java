package com.example.gusta.universidadeacme.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.adapter.TabAdapter;

public class HomeFragment extends Fragment {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new NoticiaFragment(), "Noticias");
        adapter.addFragment(new EventoFragment(), "Eventos");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }



}
