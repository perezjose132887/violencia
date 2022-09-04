package com.example.violenciacontralamujer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AlertaActivity extends AppCompatActivity {

    AlertaFragment alertaFragment= new AlertaFragment();
    EmergenciasFragment emergenciasFragment= new EmergenciasFragment();
    PreguntasFragment preguntasFragment= new PreguntasFragment();
    MenuFragment menuFragment= new MenuFragment();

    Button cerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
        cerrar=(Button) findViewById(R.id.btnCerrarSesion);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent intent=new Intent(AlertaActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(alertaFragment);

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.firstFragment:
                    loadFragment(alertaFragment);
                    return true;
                case R.id.secondFragment:
                    loadFragment(emergenciasFragment);
                    return true;
                case R.id.thirdFragment:
                    loadFragment(preguntasFragment);
                    return true;
                case R.id.fourthFragment:
                    loadFragment(menuFragment);
                    return true;
            }
            return false;
        }
    };


    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.commit();
    }


}