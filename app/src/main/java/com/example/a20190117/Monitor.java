package com.example.a20190117;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class Monitor extends Fragment {
    Handler mhandler = new Handler();
    View view;
    Status status;
    Fixing fixing;
    Heating heating;
    Vacuum vacuum;
    Release release;
    int measure;

    int step = 0;
    double value = 0;

    ButtonListener buttonListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.monitorframe, container, false);
        status = new Status();
        fixing = new Fixing();
        heating = new Heating();
        vacuum = new Vacuum();
        release = new Release();
        buttonListener = new ButtonListener();

        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();
        childFt.add(R.id.statusframe, status)
                .show(status)
                .add(R.id.measureframe, fixing)
                .add(R.id.measureframe, heating)
                .add(R.id.measureframe, vacuum)
                .add(R.id.measureframe, release)
                .commit();

        return view;
    }

    public void startSimul(){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mhandler.post(new Runnable(){
                   public void run(){
                       if(step == 0){
                           status.run(0);
                           setMeasure(0);
                           step++;
                       } else if(step == 1){
                           status.update("value : " + value);
                           fixing.update(value++, value++);
                           if(value > 20) step++;
                       } else if(step == 2){
                           status.done(0);
                           value = 0;
                           step++;
                       } else if(step == 3){
                           status.run(1);
                           setMeasure(1);
                           step++;
                       } else if(step == 4){
                           status.update("value : " + value);
                           heating.update(value++);
                           if(value > 60) step++;
                       } else if(step == 5){
                           status.done(1);
                           value = 0;
                           step++;
                       } else if(step == 6){
                           status.run(2);
                           setMeasure(2);
                           step++;
                       } else if(step == 7){
                           vacuum.updateV(value++);
                           if(value >60) step++;
                       } else if(step == 8){
                           value = 0;
                           vacuum.setIndex(1);
                           vacuum.isConnected(0);
                           step++;
                       } else if(step == 9){
                           vacuum.updateV(value++);
                           if(value >40) step++;
                       } else if(step == 10){
                           value = 0;
                           vacuum.setIndex(2);
                           vacuum.isConnected(1);
                           step++;
                       } else if(step == 11){
                           vacuum.updateV(value++);
                           if(value >40) step++;
                       } else if(step == 12){
                           value = 0;
                           vacuum.setIndex(3);
                           vacuum.isConnected(2);
                           step++;
                       } else if(step == 13){
                           status.done(2);
                           status.run(3);
                           vacuum.next();
                           step++;
                       } else if(step == 14){
                           vacuum.updateH(value++);
                           if(value>50) step++;
                       } else if(step == 15){
                           vacuum.sencerOpen();
                           step++;
                       } else if(step == 16){
                           status.done(3);
                           step++;
                       } else if(step == 17){
                           setMeasure(3);
                           status.run(4);
                           step++;
                       } else if(step == 18){
                           release.update(value--, value--);
                           if(value <= 0) step++;
                       } else if(step == 19){
                           status.done(4);
                       }
                   }
                });
            }
        },200, 100);
    }

    //초기화
    public void set(double xSetting, double ySetting, double zSetting, double temp){
        measure = 0;
        setMeasure(measure);
        status.set();
        fixing.set(xSetting, ySetting);
        heating.set(temp);
        vacuum.set(zSetting);
        release.set(xSetting, ySetting);
    }

    public void setMeasure(int n){
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        measure = n;

        Button left = (Button) view.findViewById(R.id.leftButton);
        left.setVisibility(View.VISIBLE);
        Button right = (Button) view.findViewById(R.id.rightButton);
        right.setVisibility(View.VISIBLE);

        switch(measure){
            case 0:
                childFt.show(fixing)
                        .hide(heating)
                        .hide(vacuum)
                        .hide(release)
                        .commit();
                left.setVisibility(View.INVISIBLE);
                break;
            case 1:
                childFt.show(heating)
                        .hide(fixing)
                        .hide(vacuum)
                        .hide(release)
                        .commit();
                break;
            case 2:
                childFt.show(vacuum)
                        .hide(fixing)
                        .hide(heating)
                        .hide(release)
                        .commit();
                break;
            case 3:
                childFt.show(release)
                        .hide(fixing)
                        .hide(heating)
                        .hide(vacuum)
                        .commit();
                right.setVisibility(View.INVISIBLE);
                break;
        }
    }

    class ButtonListener implements Button.OnClickListener {
        Button left, right;

        public ButtonListener(){
            left = (Button) view.findViewById(R.id.leftButton);
            left.setOnClickListener(this);

            right = (Button) view.findViewById(R.id.rightButton);
            right.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.leftButton:
                    setMeasure(--measure);
                    break;
                case R.id.rightButton:
                    setMeasure(++measure);
                    break;
            }
        }
    }
}