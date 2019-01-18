package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Setting extends Fragment {
    View view;

    double xSetting, ySetting, zSetting, temp;

    Callback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.settingframe, container, false);

        callback = (Callback) getActivity();

        getValues();
        dataToMain();

        return view;
    }

    //메인에 저장하라 전송
    private void dataToMain(){
        callback.setValue(xSetting, ySetting, zSetting, temp);
    }

    //파일로 저장된 값 불러오기
    private void getValues(){
        //밑 2줄은 임시 0으로 초기화
        xSetting = ySetting = zSetting = temp = 0;
    }

    //파일로 저장 하는 함수 xml 저장 버튼에 반응
    private void setValues(){
        //텍스트들 읽어서 파일로 저장
        getValues();
        dataToMain();
    }
}
