package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Monitor extends Fragment {
    View view;
    View status;
    Fixing fixing;
    Heating heating;
    Vacuum vacuum;
    Disassembly disassembly;
    Release release;
    int state = 0;

    ButtonListener buttonListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.monitorframe, container, false);
        status = view.findViewById(R.id.status);
        fixing = new Fixing();
        heating = new Heating();
        vacuum = new Vacuum();
        disassembly = new Disassembly();
        release = new Release();
        buttonListener = new ButtonListener();

        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();
        childFt.add(R.id.measureframe, fixing)
                .add(R.id.measureframe, heating)
                .add(R.id.measureframe, vacuum)
                .add(R.id.measureframe, disassembly)
                .add(R.id.measureframe, release)
                .commit();

        setMeasure(state);

        return view;
    }

    public void setMeasure(int n){
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        state = n;

        Button left = (Button) view.findViewById(R.id.leftButton);
        left.setVisibility(View.VISIBLE);
        Button right = (Button) view.findViewById(R.id.rightButton);
        right.setVisibility(View.VISIBLE);

        switch(state){
            case 0:
                childFt.show(fixing)
                        .hide(heating)
                        .hide(vacuum)
                        .hide(disassembly)
                        .hide(release)
                        .commit();
                left.setVisibility(View.INVISIBLE);
                break;
            case 1:
                childFt.show(heating)
                        .hide(fixing)
                        .hide(vacuum)
                        .hide(disassembly)
                        .hide(release)
                        .commit();
                break;
            case 2:
                childFt.show(vacuum)
                        .hide(fixing)
                        .hide(heating)
                        .hide(disassembly)
                        .hide(release)
                        .commit();
                break;
            case 3:
                childFt.show(disassembly)
                        .hide(fixing)
                        .hide(heating)
                        .hide(vacuum)
                        .hide(release)
                        .commit();
                break;
            case 4:
                childFt.show(release)
                        .hide(fixing)
                        .hide(heating)
                        .hide(vacuum)
                        .hide(disassembly)
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
                    setMeasure(--state);
                    break;
                case R.id.rightButton:
                    setMeasure(++state);
                    break;
            }
        }
    }
}