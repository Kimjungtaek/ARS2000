package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Fragment {
    View view;
    View xedit, yedit, zedit, tedit;
    EditText xValue, yValue, zValue, tValue;
    Button confirm, xup, xdown, yup, ydown, zup, zdown, tup, tdown;

    boolean editable = true;
    double xSetting, ySetting, zSetting, temp;
    ButtonListener buttonListener;

    Callback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.settingframe, container, false);

        xedit = (View) view.findViewById(R.id.xsetting);
        TextView xtitle = (TextView) xedit.findViewById(R.id.title);
        xtitle.setText("xSetting");
        xValue = (EditText) xedit.findViewById(R.id.value);
        yedit = (View) view.findViewById(R.id.ysetting);
        TextView ytitle = (TextView) yedit.findViewById(R.id.title);
        ytitle.setText("ySetting");
        yValue = (EditText) yedit.findViewById(R.id.value);
        zedit = (View) view.findViewById(R.id.zsetting);
        TextView ztitle = (TextView) zedit.findViewById(R.id.title);
        ztitle.setText("zSetting");
        zValue = (EditText) zedit.findViewById(R.id.value);
        tedit = (View) view.findViewById(R.id.temp);
        TextView ttitle = (TextView) tedit.findViewById(R.id.title);
        ttitle.setText("temp");
        tValue = (EditText) tedit.findViewById(R.id.value);

        buttonListener = new ButtonListener();

        callback = (Callback) getActivity();

        dataToMain();

        return view;
    }

    //메인에 저장하라 전송
    public void dataToMain(){
        getValues();
        callback.setValue(xSetting, ySetting, zSetting, temp);
    }

    //파일로 저장된 값 불러오기
    private void getValues(){
        //밑 줄은 임시로 초기화
        xSetting = 70;
        ySetting = 20;
        zSetting = 50;
        temp = 100;

        xValue.setText(Double.toString(xSetting));
        yValue.setText(Double.toString(ySetting));
        zValue.setText(Double.toString(zSetting));
        tValue.setText(Double.toString(temp));
    }

    //파일로 저장 하는 함수 xml 저장 버튼에 반응
    private void setValues(){
        //텍스트들 읽어서 파일로 저장
        dataToMain();
    }

    public void setEditable(boolean set){
        editable = set;
        if(set) {
            confirm.setVisibility(view.VISIBLE);
        }
        else{
            confirm.setVisibility(view.INVISIBLE);
        }

    }
    class ButtonListener implements Button.OnClickListener{
        public ButtonListener(){
            confirm = view.findViewById(R.id.confirm);
            confirm.setOnClickListener(this);
            xup = xedit.findViewById(R.id.upButton);
            xup.setOnClickListener(this);
            xdown = xedit.findViewById(R.id.downButton);
            xdown.setOnClickListener(this);
            yup = yedit.findViewById(R.id.upButton);
            yup.setOnClickListener(this);
            ydown = yedit.findViewById(R.id.downButton);
            ydown.setOnClickListener(this);
            zup = zedit.findViewById(R.id.upButton);
            zup.setOnClickListener(this);
            zdown = zedit.findViewById(R.id.downButton);
            zdown.setOnClickListener(this);
            tup = tedit.findViewById(R.id.upButton);
            tup.setOnClickListener(this);
            tdown = tedit.findViewById(R.id.downButton);
            tdown.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.confirm){
                setValues();
            } else if(v.getId() == xup.getId()){
                double d = Double.parseDouble(xValue.getText().toString()) + 1;
                xValue.setText(Double.toString(d));
            } else if(v.getId() == xdown.getId()){
                double d = Double.parseDouble(xValue.getText().toString()) - 1;
                xValue.setText(Double.toString(d));
            } else if(v.getId() == yup.getId()){
                double d = Double.parseDouble(yValue.getText().toString()) + 1;
                yValue.setText(Double.toString(d));
            } else if(v.getId() == ydown.getId()){
                double d = Double.parseDouble(yValue.getText().toString()) - 1;
                yValue.setText(Double.toString(d));
            } else if(v.getId() == zup.getId()){
                double d = Double.parseDouble(zValue.getText().toString()) + 1;
                zValue.setText(Double.toString(d));
            } else if(v.getId() == zdown.getId()){
                double d = Double.parseDouble(zValue.getText().toString()) - 1;
                zValue.setText(Double.toString(d));
            } else if(v.getId() == tup.getId()){
                double d = Double.parseDouble(tValue.getText().toString()) + 1;
                tValue.setText(Double.toString(d));
            } else if(v.getId() == tdown.getId()){
                double d = Double.parseDouble(tValue.getText().toString()) - 1;
                tValue.setText(Double.toString(d));
            }
        }
    }

}
