package com.example.a20190117;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log extends Fragment {
    View view;
    TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.logframe, container, false);
        text = view.findViewById(R.id.log);
        text.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

    public void add(char tag, String ab, String content){
        Date date = new Date();
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
        String formatDate = sdfNow.format(date);

        text.setText(text.getText() + "\n"+ tag + ")[" + formatDate + "] " + ab +" : " + content);
    }
}
