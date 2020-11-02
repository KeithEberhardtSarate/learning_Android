package uniftec.keith.avaliacao.dois.com.br;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText inputGrausCelsius;
    private Button btnCalcular;
    private Button btnLimpar;
    private TextView labelGrausFahrenheit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputGrausCelsius = findViewById(R.id.inputGrausCelsius);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);
        labelGrausFahrenheit = findViewById(R.id.labelGrausFahrenheit);

        btnCalcular.setOnClickListener(this);
        btnLimpar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnCalcular){
            DecimalFormat df = new DecimalFormat("0.0");
            NumberFormat nf = new DecimalFormat("00");

            Float grausFahrenheit = convertToFahrenheit(Float.valueOf(inputGrausCelsius.getText().toString()));

            if(grausFahrenheit == Math.rint (grausFahrenheit)){
                labelGrausFahrenheit.setText(nf.format(grausFahrenheit));
            }else{
                labelGrausFahrenheit.setText(df.format(grausFahrenheit));
            }
        }

        if(view == btnLimpar){
            limpar();
        }
    }

    private Float convertToFahrenheit(Float grausCelsius){
        Float grausFahrenheit = ((9 * grausCelsius) + 160) / 5;

        return grausFahrenheit;
    }

    private void limpar(){
        inputGrausCelsius.setText("");
        labelGrausFahrenheit.setText("");
    }
}