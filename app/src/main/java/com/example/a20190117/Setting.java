package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Setting extends Fragment {
    View view;
    Edit xedit, yedit, zedit, tedit;
    Button confirm;

    boolean editable = true;
    double xSetting, ySetting, zSetting, temp;

    Callback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.settingframe, container, false);

        xedit = new Edit(R.id.xsetting,"xSetting");
        yedit = new Edit(R.id.ysetting, "ySetting");
        zedit = new Edit(R.id.zsetting, "zSetting");
        tedit = new Edit(R.id.temp, "Temperature");

        confirm = (Button) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                setValues();
            }
        });

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

        xedit.set(xSetting);
        yedit.set(ySetting);
        zedit.set(zSetting);
        tedit.set(temp);
    }

    //파일로 저장 하는 함수 xml 저장 버튼에 반응
    private void setValues(){
        // 텍스트들 읽어서 파일로 저장
        // xedit.get() 등등
        // xml 파싱으로 파일로
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

    class Edit implements Button.OnClickListener{
        View edit;
        TextView title;
        EditText text;
        Button up, down;
        double value;

        public Edit(int id, String titleName){
            edit = view.findViewById(id);
            title = edit.findViewById(R.id.title);
            text = edit.findViewById(R.id.value);
            up = edit.findViewById(R.id.upButton);
            down = edit.findViewById(R.id.downButton);

            up.setOnClickListener(this);
            down.setOnClickListener(this);

            title.setText(titleName);
        }

        public double get(){
            return value;
        }

        public void set(double d){
            value = d;
            text.setText("" + d);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.upButton){
                double d = Double.parseDouble(text.getText().toString()) + 1;
                text.setText("" + d);
            } else if(v.getId() == R.id.downButton){
                double d = Double.parseDouble(text.getText().toString()) - 1;
                text.setText("" + d);
            }
        }
    }
}
