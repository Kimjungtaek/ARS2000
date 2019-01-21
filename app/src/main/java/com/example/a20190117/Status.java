package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Status extends Fragment {
    View view;
    LinearLayout fix, heat, vacuum, dis, release;
    TextView fixT, heatT, vacuumT, disT, releaseT;

    int layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.status, container, false);

        fix = (LinearLayout) view.findViewById(R.id.stateFix);
        heat = (LinearLayout) view.findViewById(R.id.stateHeating);
        vacuum = (LinearLayout) view.findViewById(R.id.stateVacuum);
        dis = (LinearLayout) view.findViewById(R.id.stateDisassembly);
        release = (LinearLayout) view.findViewById(R.id.stateRelease);

        fixT = (TextView) view.findViewById(R.id.stateFixText);
        heatT = (TextView) view.findViewById(R.id.stateHeatingText);
        vacuumT = (TextView) view.findViewById(R.id.stateVacuumText);
        disT = (TextView) view.findViewById(R.id.stateDisassemblyText);
        releaseT = (TextView) view.findViewById(R.id.stateReleaseText);

        return view;
    }

    //초기화
    public void set(){
        layout = 0;
        setReady(fixT);
        while(fix.getChildCount() != 1) fix.removeViewAt(1);
        setReady(heatT);
        while(heat.getChildCount() != 1) heat.removeViewAt(1);
        setReady(vacuumT);
        while(vacuum.getChildCount() != 1) vacuum.removeViewAt(1);
        setReady(disT);
        while(dis.getChildCount() != 1) fix.removeViewAt(1);
        setReady(releaseT);
        while(release.getChildCount() != 1) release.removeViewAt(1);
    }

    public void setLayout(int n){
        layout = n;
    }

    public void update(String string){
        LinearLayout linear;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20,0,20,0);
        TextView text;

        if(layout == 0){
            linear = fix;
            text = new TextView(linear.getContext());
        } else if(layout == 1){
            linear = heat;
            text = new TextView(linear.getContext());
        } else if(layout == 2){
            linear = vacuum;
            text = new TextView(linear.getContext());
        } else if(layout == 3){
            linear = dis;
            text = new TextView(linear.getContext());
        } else {
            linear = release;
            text = new TextView(linear.getContext());
        }
        text.setText(string);
        text.setTextSize(15);
        text.setTextColor(getResources().getColor(R.color.colorPrimaryText));
        linear.addView(text, params);
    }

    private void setReady(TextView t){
        int size = 90;

        t.setTextSize(25);
        t.setWidth(size);
        t.setHeight(size);
        t.setBackgroundResource(R.color.colorGreenWhite);
    }

    private void setRunning(TextView t){
        int size = 120;

        t.setTextSize(40);
        t.setWidth(size);
        t.setHeight(size);
        t.setBackgroundResource(R.color.colorGreen);
    }

    private void setDone(TextView t){
        int size = 90;

        t.setTextSize(25);
        t.setWidth(size);
        t.setHeight(size);
        t.setBackgroundResource(R.color.colorGreenDark);
    }
}
