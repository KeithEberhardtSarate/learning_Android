package uniftec.keith.com.br.cartaoponto;

import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

public class ChronometerTimer {
    public Chronometer chronometer;
    private boolean running;
    private long pauseOffset;

    public ChronometerTimer(Chronometer componentView){
        chronometer = componentView;
    }

    public void start() {
        if(!running){
            chronometer.setVisibility(View.VISIBLE);
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pause() {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void reset() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public long getPauseOffset() {
        return pauseOffset;
    }
}
