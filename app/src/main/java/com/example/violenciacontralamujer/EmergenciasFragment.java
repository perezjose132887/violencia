package com.example.violenciacontralamujer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmergenciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergenciasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton patrulla,bombero,transito;
    View vista;
    public EmergenciasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergenciasFragment newInstance(String param1, String param2) {
        EmergenciasFragment fragment = new EmergenciasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista =inflater.inflate(R.layout.fragment_emergencias, container, false);
        patrulla=(ImageButton) vista.findViewById(R.id.ibPatrulla);
        bombero=(ImageButton) vista.findViewById(R.id.ibBomberos);
        transito=(ImageButton) vista.findViewById(R.id.ibTransito);

        patrulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone= "tel:110";
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);
            }
        });

        bombero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone= "tel:119";
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);
            }
        });

        transito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone= "tel:111";
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);
            }
        });

        return vista;
    }
}