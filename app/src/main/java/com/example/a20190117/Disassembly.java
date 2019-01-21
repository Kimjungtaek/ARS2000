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

public class Disassembly extends Fragment {
    View view;
    Timer timer;

    TextView sol1, sol2, sol3, sol4, sol5, sol6, sol7;
    double val1, val2, val3, val4, val5, val6, val7;

    ProgressBar progress;
    TextView text;
    double height, setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.disassembly, container, false);

        sol1 = (TextView) view.findViewById(R.id.sol1);
        sol2 = (TextView) view.findViewById(R.id.sol2);
        sol3 = (TextView) view.findViewById(R.id.sol3);
        sol4 = (TextView) view.findViewById(R.id.sol4);
        sol5 = (TextView) view.findViewById(R.id.sol5);
        sol6 = (TextView) view.findViewById(R.id.sol6);
        sol7 = (TextView) view.findViewById(R.id.sol7);

        progress = (ProgressBar) view.findViewById(R.id.progress);
        text = (TextView) view.findViewById(R.id.text);

        return view;
    }

    public void update(double val1, double val2, double val3, double val4, double val5, double val6, double val7, double height){
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
        this.val6 = val6;
        this.val7 = val7;
        this.height = height;
    }

    public void set(double setting){
        this.height = 0;
        this.setting = setting;
        val1 = val2 = val3 = val4 = val5 = val6 = val7 = 0;
        sol1.setText(val1 + "\n" + "kPas");
        sol2.setText(val2 + "\n" + "kPas");
        sol3.setText(val3 + "\n" + "kPas");
        sol4.setText(val4 + "\n" + "kPas");
        sol5.setText(val5 + "\n" + "kPas");
        sol6.setText(val6 + "\n" + "kPas");
        sol7.setText(val7 + "\n" + "kPas");
        progress.setProgress((int) height);
        progress.setMax((int) setting);
        text.setText(height + " / " + setting + "mm");
    }

    public void run(){
        timer = new Timer();
        timer.schedule(new ThisTimerTask(),200, 100);
    }

    public void stop(){
        timer.cancel();
    }

    class ThisTimerTask extends TimerTask {
        public void run(){
            sol1.setText(val1 + "\n" + "kPas");
            sol2.setText(val2 + "\n" + "kPas");
            sol3.setText(val3 + "\n" + "kPas");
            sol4.setText(val4 + "\n" + "kPas");
            sol5.setText(val5 + "\n" + "kPas");
            sol6.setText(val6 + "\n" + "kPas");
            sol7.setText(val7 + "\n" + "kPas");
            progress.setProgress((int) height);
            text.setText(height + " / " + setting + "mm");
        }
    }
}
