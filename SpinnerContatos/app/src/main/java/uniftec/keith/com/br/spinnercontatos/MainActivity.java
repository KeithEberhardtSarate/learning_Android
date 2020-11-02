package uniftec.keith.com.br.spinnercontatos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner spinner;
    private Button btnPassarDados;
    private Cursor cursor;
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]
                {Manifest.permission.READ_CONTACTS},1);

        btnPassarDados = findViewById(R.id.btnPassarDados);
        spinner = (Spinner) findViewById(R.id.spinnerContatos);

        items = new ArrayList<String>();

        items.add(getString(R.string.contatos));

        btnPassarDados.setOnClickListener(this);
    }

    public void onClick(View view) {

        if(view == btnPassarDados){
            Uri caminho = ContactsContract.Contacts.CONTENT_URI;
            ContentResolver contentResolver = MainActivity.this.getContentResolver();
            cursor = contentResolver.query(caminho, null, null, null,ContactsContract.Contacts.DISPLAY_NAME);

            if (cursor.getCount() > 0)
            {

                while (cursor.moveToNext())
                {
                    String nome = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    items.add(nome);
                }
            }

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}