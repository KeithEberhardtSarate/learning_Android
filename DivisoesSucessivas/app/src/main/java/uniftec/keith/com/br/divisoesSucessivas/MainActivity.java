package uniftec.keith.com.br.divisoesSucessivas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputValor1;
    private EditText inputValor2;
    private EditText inputValor3;
    private EditText inputValor4;

    private Button btnCalcular;
    private Button btnLimpar;
    private Button btnPassarDados;

    private TextView lblResultado;

    private Double valor1;
    private Double valor2;
    private Double valor3;
    private Double valor4;
    private String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValor1 = findViewById(R.id.editTextValor1);
        inputValor2 = findViewById(R.id.editTextValor2);
        inputValor3 = findViewById(R.id.editTextValor3);
        inputValor4 = findViewById(R.id.editTextValor4);

        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnPassarDados = findViewById(R.id.btnPassarDados);

        btnCalcular.setOnClickListener(this);
        btnLimpar.setOnClickListener(this);
        btnPassarDados.setOnClickListener(this);

        lblResultado = findViewById(R.id.lblResultado);

        lblResultado.setText(getString(R.string.resultado, ""));

        resultado = "";
    }

    @Override
    public void onClick(View view) {
        hideSoftKeyboard(this);

        if(view == btnCalcular){
            try {
                valor1 = Double.parseDouble(inputValor1.getText().toString());
                valor2 = Double.parseDouble(inputValor2.getText().toString());
                valor3 = Double.parseDouble(inputValor3.getText().toString());
                valor4 = Double.parseDouble(inputValor4.getText().toString());

                if(valor1 == 0 || valor2 == 0 || valor3 == 0 || valor4 == 0){
                    throw new ArithmeticException();
                }

                Double resultadoDivisao = calculaDivisao(valor1, valor2, valor3, valor4);

                resultado = resultadoDivisao.toString();

                lblResultado.setText(getString(R.string.resultado, resultadoDivisao.toString()));
            } catch (NumberFormatException ex) {
                Toast.makeText(this, R.string.msgCampoObrigatorio, Toast.LENGTH_SHORT).show();
            } catch (ArithmeticException ex) {
                Toast.makeText(this, R.string.msgValorMaiorQueZero, Toast.LENGTH_SHORT).show();
            }
        }

        if(view == btnLimpar){
            limpar();
        }

        if(view == btnPassarDados){
            try {
                valor1 = Double.parseDouble(inputValor1.getText().toString());
                valor2 = Double.parseDouble(inputValor2.getText().toString());
                valor3 = Double.parseDouble(inputValor3.getText().toString());
                valor4 = Double.parseDouble(inputValor4.getText().toString());

                if(valor1 == 0 || valor2 == 0 || valor3 == 0 || valor4 == 0){
                    throw new ArithmeticException();
                }

                Intent intent = new Intent(this, Resultado.class);
                intent.putExtra("valor1", valor1.toString());
                intent.putExtra("valor2", valor2.toString());
                intent.putExtra("valor3", valor3.toString());
                intent.putExtra("valor4", valor4.toString());
                intent.putExtra("resultado", resultado);
                startActivity(intent);

            } catch (NumberFormatException ex) {
                Toast.makeText(this, R.string.msgPreenchaOsValoresPrimeiramente, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Double calculaDivisao(Double valor1, Double valor2, Double valor3, Double valor4){
        Double divsao1 = valor1 / valor2;
        Double divsao2 = divsao1 / valor3;
        Double Resultado = divsao2 / valor4;

        return Resultado;
    }

    private void limpar(){
        inputValor1.setText("");
        inputValor2.setText("");
        inputValor3.setText("");
        inputValor4.setText("");
        lblResultado.setText(getString(R.string.resultado, ""));
        inputValor1.requestFocus();
    }

    private void hideSoftKeyboard(Activity mActivity) {
        try {
            View view = mActivity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}