package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Setting extends Fragment {
    View view;

    double heat = 11.1;
    double temp = 22.2;
    double disassembly = 33.3;

    Callback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.settingframe, container, false);

        callback = (Callback) getActivity();

        //파일 저장 된 값 불러오기
        setValues();

        return view;
    }

    private void setValues(){
        callback.setValue(heat, temp, disassembly);
    }
}
