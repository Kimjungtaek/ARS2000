package com.example.a20190117;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Fixing extends Fragment {
    View view;
    Timer timer;

    ProgressBar progressX, progressY;
    TextView xText, yText;
    double xValue = 0, yValue = 0, xSetting, ySetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fixing, container, false);

        progressX = (ProgressBar) view.findViewById(R.id.progressX);
        progressY = (ProgressBar) view.findViewById(R.id.progressY);
        xText = (TextView) view.findViewById(R.id.xValue);
        yText = (TextView) view.findViewById(R.id.yValue);

        return view;
    }

    public void update(double xValue, double yValue){
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public void set(double xSetting, double ySetting){
        this.xSetting = xSetting;
        this.ySetting = ySetting;
    }

    public void run(){
        timer = new Timer();
        timer.schedule(new ThisTimerTask(),200,100);
    }

    public void stop(){
        timer.cancel();
    }

    class ThisTimerTask extends TimerTask {
        public void run() {
            progressX.setProgress((int) xValue);
            xText.setText(xValue + " / " + xSetting);
            progressY.setProgress((int) yValue);
            yText.setText(yValue + " / " + ySetting);
        }
    }
}
