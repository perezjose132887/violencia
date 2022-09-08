package com.example.violenciacontralamujer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.violenciacontralamujer.Model.ContactosModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlertaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertaFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Todo esto es para los contactos
    View vista;
    ImageButton llamar;
    ArrayList<ContactosModel> listaContactos;

    //To esto es para mandar coordenadas
    EditText latitud,longitud;



    public AlertaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlertaFragment newInstance(String param1, String param2) {
        AlertaFragment fragment = new AlertaFragment();
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
        vista=inflater.inflate(R.layout.fragment_alerta, container, false);
        llamar=(ImageButton) vista.findViewById(R.id.btnAyuda);
        latitud=(EditText) vista.findViewById(R.id.etLatitud);
        longitud=(EditText) vista.findViewById(R.id.etLongitud);



        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(getContext(),"administracion",null,1);

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Permiso para enviar mensajes de ayuda
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},1);
                }
                //Permiso para enviar coordenadas
                int permissionCheck=ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION);
                if(permissionCheck==PackageManager.PERMISSION_DENIED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){

                    }else{
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
                    }
                }
                try{




                    LocationManager locationManager=(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    LocationListener locationListener=new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            latitud.setText(""+location.getLatitude());
                            longitud.setText(""+location.getLongitude());
                        }
                        public void onStatusChanged(String provider, int status, Bundle extras){}
                        public void onProviderEnabled(String provider){}
                        public void onProviderDisabled(String provider){}

                    };

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);









                    SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();
                    Cursor cursor= BaseDeDatos.rawQuery("SELECT * FROM contactos",null);
                    ArrayList<ContactosModel> lista=new ArrayList<ContactosModel>();
                    while(cursor.moveToNext()){
                        ContactosModel cm=new ContactosModel();
                        cm.setId(cursor.getInt(0));
                        cm.setContacto(cursor.getString(1));
                        cm.setTelefono(cursor.getString(2));
                        lista.add(cm);
                    }


                    String mensajeAyuda="Ayuda: \n https://maps.google.com/?q="+latitud.getText().toString().trim()+","+longitud.getText().toString().trim();

                    for(int i=0; i<lista.size(); i++){


                        //Envia mensajes a todos los contactos seleccionados
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(lista.get(i).getTelefono(),null,mensajeAyuda,null,null);


                    }
                    Toast.makeText(getContext(), "SmsEnviado", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), mensajeAyuda, Toast.LENGTH_SHORT).show();




                    String phone= "tel:911";
                    Intent intent=new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(phone));
                    startActivity(intent);







                }
                catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        return vista;



    }





}