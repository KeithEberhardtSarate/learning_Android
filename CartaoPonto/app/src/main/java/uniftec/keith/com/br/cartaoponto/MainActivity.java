package uniftec.keith.com.br.cartaoponto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int[] daysOfWeek = {R.string.dayOfWeek6,R.string.dayOfWeek0,R.string.dayOfWeek1,R.string.dayOfWeek2,R.string.dayOfWeek3,R.string.dayOfWeek4,R.string.dayOfWeek5};

    // View bindings
    private TextView lblCurrentDate;
    private TextView lblCheck;
    private TextView lblResult;
    private ImageButton btnFingerPrint;

    // Model variables
    private String morningCheckInTime;
    private String morningCheckOutTime;
    private String afternoonCheckInTime;
    private String afternoonCheckOutTime;

    private ChronometerTimer workingTimerChronometer;
    private ChronometerTimer lunchTimeTimerChronometer;

    private Date today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblCurrentDate = findViewById(R.id.lblCurrentDate);
        lblCheck = findViewById(R.id.lblCheck);
        btnFingerPrint = findViewById(R.id.btnFingerPrint);

        workingTimerChronometer = new ChronometerTimer((Chronometer) findViewById(R.id.workingTimerChronometer));
        workingTimerChronometer.chronometer.setVisibility(View.INVISIBLE);
        workingTimerChronometer.chronometer.setFormat(getString(R.string.workingTime));

        lunchTimeTimerChronometer = new ChronometerTimer((Chronometer) findViewById(R.id.lunchTimeTimerChronometer));
        lunchTimeTimerChronometer.chronometer.setVisibility(View.INVISIBLE);
        lunchTimeTimerChronometer.chronometer.setFormat(getString(R.string.lunchTime));

        today = new Date();

        lblCurrentDate.setText(getString(R.string.currentDate, getCurrentDayOfWeek(today), getCurrentDate(today)));
    }

    @Override
    public void onClick(View view) {
        if(view == btnFingerPrint){
            Date currentDate = new Date();

            if(morningCheckInTime == null){
                morningCheckInTime = getCurrentTime(currentDate);
                lblCheck.setText(getString(R.string.str_check, morningCheckInTime, "X", "X", "X"));
                workingTimerChronometer.start();
            }else if (morningCheckOutTime == null){
                morningCheckOutTime = getCurrentTime(currentDate);
                lblCheck.setText(getString(R.string.str_check, morningCheckInTime, morningCheckOutTime, "X", "X"));
                workingTimerChronometer.pause();
                lunchTimeTimerChronometer.start();
            }else if (afternoonCheckInTime == null){
                afternoonCheckInTime = getCurrentTime(currentDate);
                lblCheck.setText(getString(R.string.str_check, morningCheckInTime, morningCheckOutTime, afternoonCheckInTime, "X"));
                lunchTimeTimerChronometer.pause();
                workingTimerChronometer.start();
            }else if (afternoonCheckOutTime == null){
                afternoonCheckOutTime = getCurrentTime(currentDate);
                lblCheck.setText(getString(R.string.str_check, morningCheckInTime, morningCheckOutTime, afternoonCheckInTime, afternoonCheckOutTime));
                workingTimerChronometer.pause();
            }else{
                Toast.makeText(this, R.string.endOfficeTimeMsg,  Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getCurrentDate(Date date) {
        SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy");
        return formatData.format(date);
    }

    private String getCurrentDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        return getString(daysOfWeek[day]);
    }

    private String getCurrentTime(Date date) {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");

        Calendar  cal = Calendar.getInstance();
        cal.setTime(date);
        Date currentTime = cal.getTime();

       return dateFormat_hora.format(currentTime);
    }
}