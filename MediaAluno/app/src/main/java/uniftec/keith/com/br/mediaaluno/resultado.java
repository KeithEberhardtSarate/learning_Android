package uniftec.keith.com.br.mediaaluno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class resultado extends AppCompatActivity {
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        resultado = findViewById(R.id.resultado);

        Intent intent = getIntent();

        String notaGrauA = intent.getStringExtra("notaGrauA");
        String notaGrauB = intent.getStringExtra("notaGrauB");
        String notaGrauC = intent.getStringExtra("notaGrauC");
        if(notaGrauC.isEmpty()){
            notaGrauC = "N/A";
        }
        String resultadoFinal = intent.getStringExtra("resultadoFinal");
        String mediaFinal = intent.getStringExtra("mediaFinal");

        resultado.setText(getString(R.string.resultadoFull2, notaGrauA, notaGrauB, notaGrauC, mediaFinal, resultadoFinal));

    }
}