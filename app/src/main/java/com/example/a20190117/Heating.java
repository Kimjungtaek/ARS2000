package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Heating extends Fragment {
    View view;
    Timer timer;

    ProgressBar progress;
    TextView text;
    double temp, setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.heating, container, false);

        progress = (ProgressBar) view.findViewById(R.id.progress);
        text = (TextView) view.findViewById(R.id.text);

        return view;
    }

    public void update(double temp){
        this.temp = temp;
    }

    public void set(double setting){
        this.temp = 0;
        this.setting = setting;
        progress.setProgress((int) temp);
        progress.setMax((int) setting);
        text.setText(temp + " / " + setting + "°C");
    }

    public void run(){
        timer = new Timer();
        timer.schedule(new ThisTimerTask(),200, 100);
    }

    public void stop(){
        timer.cancel();
    }

    class ThisTimerTask extends TimerTask {
        public void run() {
            progress.setProgress((int) temp);
            text.setText(temp + " / " + setting + "°C");
        }
    }
}
