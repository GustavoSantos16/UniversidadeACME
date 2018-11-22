package com.example.gusta.universidadeacme.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.SharedPreferencesConfig;

import net.glxn.qrgen.android.QRCode;


public class QrcodeFragment extends Fragment {

    private SharedPreferencesConfig preferencesConfig;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qrcode, null);

        preferencesConfig = new SharedPreferencesConfig(getContext());

        ImageView img_qrCode = view.findViewById(R.id.img_qrcode);

        final String matricula = preferencesConfig.readUsuarioMatricula();

        Bitmap myBitmap = QRCode.from(matricula).withSize(300,300).bitmap();
        img_qrCode.setImageBitmap(myBitmap);

        Button qr_code_toast = view.findViewById(R.id.qr_code_toast);

        qr_code_toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),""+matricula+"",
                        Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}
