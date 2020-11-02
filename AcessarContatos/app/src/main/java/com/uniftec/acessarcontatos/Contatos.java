package com.uniftec.acessarcontatos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Contatos extends AppCompatActivity {

    private Spinner spnNome,spnTelefone;
    private Button btnContatos;
    private Cursor cursor;
    private boolean carregouContatos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);
        spnNome=(Spinner)findViewById(R.id.spnNome);
        spnTelefone=(Spinner)findViewById(R.id.spnTelefone);
        btnContatos = (Button)findViewById(R.id.btnContatos);

        ActivityCompat.requestPermissions(Contatos.this,new String[]
                {Manifest.permission.READ_CONTACTS},1);

        spnNome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicaoSelecionada, long l) {
                spnTelefone.setSelection(posicaoSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spnTelefone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicaoSelecionada, long l) {
                spnNome.setSelection(posicaoSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btnContatos.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (carregouContatos == false)
                {
                    Uri caminho = ContactsContract.Contacts.CONTENT_URI;
                    ContentResolver contentResolver = Contatos.this.getContentResolver();
                    cursor = contentResolver.query(caminho, null, null, null,ContactsContract.Contacts.DISPLAY_NAME);
                    if (cursor.getCount() > 0)
                    {
                        List<String> nomesContatos = new ArrayList<String>();
                        List<String> numerosContatos = new ArrayList<String>();
                        while (cursor.moveToNext())
                        {
                            String temNumero = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            if (temNumero.equals("1"))
                            {
                                String nome = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                nomesContatos.add(nome);
                                String contatoId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                                Cursor numero = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contatoId, null, null);
                                numero.moveToNext();
                                String telefone = numero.getString(numero.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                numerosContatos.add(telefone);
                            }
                        }
                        ArrayAdapter<String> dataAdapterNomes = new ArrayAdapter<String>(Contatos.this,R.layout.spinner_item, nomesContatos);
                        dataAdapterNomes.setDropDownViewResource(R.layout.spinner_dropdown_item);
                        spnNome.setAdapter(dataAdapterNomes);
                        ArrayAdapter<String> dataAdapterNumeros = new ArrayAdapter<String>(Contatos.this,R.layout.spinner_item, numerosContatos);
                        dataAdapterNumeros.setDropDownViewResource(R.layout.spinner_dropdown_item);
                        spnTelefone.setAdapter(dataAdapterNumeros);
                        carregouContatos = true;
                    }
                }
            }
        });


    }
}
