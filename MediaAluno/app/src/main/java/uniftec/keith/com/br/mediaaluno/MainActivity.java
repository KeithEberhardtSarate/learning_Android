package uniftec.keith.com.br.mediaaluno;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private EditText notaGrauA;
    private EditText notaGrauB;
    private EditText notaGrauC;
    private Button btnCalcular;
    private Button btnLimpar;
    private Button btnDadosProximaTela;
    private TextView resultado;

    private String resultadoFinal;
    private String mediaFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notaGrauA = findViewById(R.id.editTextNotaGrauA);
        notaGrauB = findViewById(R.id.editTextNotaGrauB);
        notaGrauC = findViewById(R.id.editTextNotaGrauC);

        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnDadosProximaTela = findViewById(R.id.btnDadosProximaTela);

        btnCalcular.setOnClickListener(this);
        btnLimpar.setOnClickListener(this);
        btnDadosProximaTela.setOnClickListener(this);

        notaGrauA.setOnKeyListener(this);
        notaGrauB.setOnKeyListener(this);
        notaGrauC.setOnKeyListener(this);

        resultado = findViewById(R.id.resultado);
    }

    @Override
    public void onClick(View view) {
        if(view == btnCalcular){
            try {
                hideSoftKeyboard(this);

                if(notaGrauA.getText().toString().isEmpty() || notaGrauB.getText().toString().isEmpty()){
                    if(!notaGrauA.getText().toString().isEmpty()){
                        notaGrauB.requestFocus();
                    }else{
                        notaGrauA.requestFocus();
                    }

                    Toast.makeText(this, getResources().getString(R.string.msgGrauAGraubObrigatorios),  Toast.LENGTH_SHORT).show();
                }else{
                    Double notaGA = Double.valueOf(notaGrauA.getText().toString().isEmpty() ? "0": notaGrauA.getText().toString());
                    Double notaGB = Double.valueOf(notaGrauB.getText().toString().isEmpty() ? "0": notaGrauB.getText().toString());

                    Double media = calculaMedia(notaGA, notaGB, null);

                    if(media > 7){
                        setResultado(getResources().getString(R.string.aprovado), media);
                    }else if(notaGrauC.getText().toString().isEmpty()){
                        setResultado(getResources().getString(R.string.reprovado), media);
                    }else{
                        Double notaGC = Double.valueOf(notaGrauC.getText().toString());

                        if(notaGC < 5){
                            setResultado(getResources().getString(R.string.reprovado), notaGC);
                        }else{
                            media = calculaMedia(notaGA, notaGB, notaGC);

                            if(media > 5){
                                setResultado(getResources().getString(R.string.aprovado), media);
                            }else{
                                setResultado(getResources().getString(R.string.reprovado), media);
                            }
                        }
                    }
                }
            }catch (NumberFormatException ex){
                Toast.makeText(this, ex.toString(),  Toast.LENGTH_LONG).show();
            }
        }

        if(view == btnLimpar){
            limpar();
        }

        if(view == btnDadosProximaTela){
            if(resultadoFinal == null || resultadoFinal.isEmpty()){
                Toast.makeText(this, R.string.msgCalculePrimeiro,  Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(this, resultado.class);
                intent.putExtra("notaGrauA", notaGrauA.getText().toString());
                intent.putExtra("notaGrauB", notaGrauB.getText().toString());
                intent.putExtra("notaGrauC", notaGrauC.getText().toString());
                intent.putExtra("resultadoFinal", resultadoFinal);
                intent.putExtra("mediaFinal", mediaFinal);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(view == notaGrauA){
            if(keyEvent.getAction() == keyEvent.ACTION_UP){
                Double notaGA = Double.valueOf(notaGrauA.getText().toString().isEmpty() ? "0": notaGrauA.getText().toString());
                if(notaGA > 10){
                    notaGrauA.setText("10");
                    notaGrauA.setSelection(notaGrauA.getText().length());
                    Toast.makeText(this, R.string.msgGrauAMaiorQue10,  Toast.LENGTH_SHORT).show();
                }
            }
            return super.onKeyDown(i, keyEvent);
        }
        if(view == notaGrauB){
            if(keyEvent.getAction() == keyEvent.ACTION_UP){
                Double notaGB = Double.valueOf(notaGrauB.getText().toString().isEmpty() ? "0": notaGrauB.getText().toString());
                if(notaGB > 10){
                    notaGrauB.setText("10");
                    notaGrauB.setSelection(notaGrauB.getText().length());
                    Toast.makeText(this, R.string.msgGrauBMaiorQue10,  Toast.LENGTH_SHORT).show();
                }
            }
            return super.onKeyDown(i, keyEvent);
        }
        if(view == notaGrauC){
            if(keyEvent.getAction() == keyEvent.ACTION_UP){
                Double notaGC = Double.valueOf(notaGrauC.getText().toString().isEmpty() ? "0": notaGrauC.getText().toString());
                if(notaGC > 10){
                    notaGrauC.setText("10");
                    notaGrauC.setSelection(notaGrauC.getText().length());
                    Toast.makeText(this, R.string.msgGrauCMaiorQue10,  Toast.LENGTH_SHORT).show();
                }
            }
            return super.onKeyDown(i, keyEvent);
        }
        return false;
    }

    private Double calculaMedia(Double grauA, Double grauB, Double grauC){
        Double media = (grauA + grauB) / 2;

        if(grauC != null){
            media = (grauA + grauB + grauC) / 3;
        }

        return media;
    }

    private void setResultado(String situacao, Double media){
        DecimalFormat df = new DecimalFormat("0.0");
        NumberFormat nf = new DecimalFormat("0");

        String formattedMedia = "";

        if(media == Math.rint (media)){
            formattedMedia = nf.format(media);
        }else{
            formattedMedia = df.format(media);
        }

        mediaFinal = formattedMedia;
        resultadoFinal = situacao;

        resultado.setText(getString(R.string.resultadoFull, situacao, formattedMedia));
    }

    private void limpar(){
        notaGrauA.setText("");
        notaGrauB.setText("");
        notaGrauC.setText("");
        resultado.setText(R.string.resultado);
        notaGrauA.requestFocus();
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