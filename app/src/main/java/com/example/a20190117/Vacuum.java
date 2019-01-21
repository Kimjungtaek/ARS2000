package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Vacuum extends Fragment {
    View view;

    TextView[] sol = new TextView[7];
    double[] val = new double[7];
    boolean[] onoff = new boolean[7];
    int index;

    ProgressBar progress;
    TextView text;
    double height, setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vacuum, container, false);

        sol[0] = (TextView) view.findViewById(R.id.sol0);
        sol[1] = (TextView) view.findViewById(R.id.sol1);
        sol[2] = (TextView) view.findViewById(R.id.sol2);
        sol[3] = (TextView) view.findViewById(R.id.sol3);
        sol[4] = (TextView) view.findViewById(R.id.sol4);
        sol[5] = (TextView) view.findViewById(R.id.sol5);
        sol[6] = (TextView) view.findViewById(R.id.sol6);


        progress = (ProgressBar) view.findViewById(R.id.progress);
        text = (TextView) view.findViewById(R.id.text);

        return view;
    }

    public void sencerOpen(){
        for(int i = 0; i < onoff.length; i++){
            onoff[i] = false;
            val[i] = 0;
            sol[i].setText("OFF");
            sol[i].setBackgroundResource(R.color.vacuumOffColor);
        }
    }

    public void next(){
        TextView title = (TextView) view.findViewById(R.id.title);
        double total = 0;
        for(int i = 0; i < onoff.length; i++){
            if(onoff[i]) total += val[i];
        }
        title.setText("Disassembly : " + total + "kPas");
    }

    public void setIndex(int index){
        this.index = index;
    }

    public void updateV(double val){
        this.val[index] = val;
        this.sol[index].setText(val + "kPas");
    }

    public void updateH(double height){
        this.height = height;
        progress.setProgress((int) height);
        text.setText(height + " / " + setting + "mm");
    }

    public boolean isConnected(int i){
        if(val[i] >= 50){
            onoff[i] = true;
            sol[i].setText("ON");
            sol[i].setTextSize(20);
            sol[i].setBackgroundResource(R.color.vacuumOnColor);
        } else {
            onoff[i] = false;
            sol[i].setText("OFF");
            sol[i].setTextSize(20);
            sol[i].setBackgroundResource(R.color.vacuumOffColor);
        }
        return onoff[i];
    }

    public void set(double setting){
        this.index = 0;
        this.height = 0;
        this.setting = setting;
        for(double var : val){
            var = 0;
        }
        for(TextView var : sol){
            var.setText("0kPas");
            var.setBackgroundResource(R.color.vacuumColor);
        }
        for(boolean var : onoff){
            var = false;
        }

        progress.setProgress((int) height);
        progress.setMax((int) setting);
        text.setText(height + " / " + setting + "mm");
    }
}
