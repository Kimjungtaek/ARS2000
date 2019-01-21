package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Release extends Fragment {
    View view;

    ProgressBar progressX, progressY;
    TextView xText, yText;
    double xValue = 0, yValue = 0, xSetting, ySetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.release, container, false);

        progressX = (ProgressBar) view.findViewById(R.id.progressX);
        progressY = (ProgressBar) view.findViewById(R.id.progressY);
        xText = (TextView) view.findViewById(R.id.xValue);
        yText = (TextView) view.findViewById(R.id.yValue);

        return view;
    }

    public void update(double xValue, double yValue){
        this.xValue = xValue;
        this.yValue = yValue;
        progressX.setProgress((int) xValue);
        xText.setText(xValue + " / " + xSetting + "mm");
        progressY.setProgress((int) yValue);
        yText.setText(yValue + " / " + ySetting + "mm");
    }

    public void set(double xSetting, double ySetting){
        this.xValue = 0;
        this.xSetting = xSetting;
        progressX.setProgress((int) xValue);
        progressX.setMax((int) xSetting);
        xText.setText(xValue + " / " + xSetting + "mm");
        this.yValue = 0;
        this.ySetting = ySetting;
        progressY.setProgress((int) yValue);
        progressY.setMax((int) ySetting);
        yText.setText(yValue + " / " + ySetting + "mm");
    }
}
