package com.uniftec.acessarcontatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }
    public void run()
    {
        Intent telaContatos = new Intent(MainActivity.this, Contatos.class);
        MainActivity.this.startActivity(telaContatos);
        MainActivity.this.finish();
    }
}
