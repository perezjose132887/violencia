package com.example.violenciacontralamujer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class Contactos extends AppCompatActivity {

    EditText contacto,telefono;
    ImageButton contact;
    static final int PICK_CONTACT_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        contacto=(EditText) findViewById(R.id.etContacto);
        telefono=(EditText) findViewById(R.id.etTelefono);
        contact=(ImageButton) findViewById(R.id.ibContacto);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeleccionarContacto();
            }


        });


    }

    private void SeleccionarContacto() {
        Intent selectContactItem=new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        selectContactItem.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(selectContactItem,PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                if(cursor.moveToFirst()){
                    int columnaNombre=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int columnaNumero=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String nombre=cursor.getString(columnaNombre);
                    String numero=cursor.getString(columnaNumero);
                    contacto.setText(nombre);
                    telefono.setText(numero);

                }
            }
        }
    }





}