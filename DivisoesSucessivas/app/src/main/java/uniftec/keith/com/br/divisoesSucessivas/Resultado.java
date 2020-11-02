package uniftec.keith.com.br.divisoesSucessivas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Resultado extends AppCompatActivity {
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        resultado = findViewById(R.id.resultado);

        Intent intent = getIntent();

        String valor1 = intent.getStringExtra("valor1");
        String valor2 = intent.getStringExtra("valor2");
        String valor3 = intent.getStringExtra("valor3");
        String valor4 = intent.getStringExtra("valor4");

        String resultadoDivisao = intent.getStringExtra("resultado");

        resultado.setText(getString(R.string.resultadoFull, valor1, valor2, valor3, valor4, resultadoDivisao));
    }
}