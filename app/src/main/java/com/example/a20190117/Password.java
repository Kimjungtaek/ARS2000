package com.example.a20190117;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Password extends Fragment {
    final String password = "1234";

    View view;
    EditText text;
    Button confirm;
    ButtonListener buttonListener = new ButtonListener();

    private Callback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.passwordframe, container, false);
        text = view.findViewById(R.id.edittext);
        text.setMovementMethod(new ScrollingMovementMethod());
        text.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    buttonListener.onClick(confirm);
                    return true;
                }
                return false;
            }
        });
        confirm = (Button) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(buttonListener);

        callback = (Callback) getActivity();

        return view;
    }

    class ButtonListener implements Button.OnClickListener{

        @Override
        public void onClick(View v) {
            if(text.getText().toString().compareTo(password) == 0){
                text.setText("");
                // 비밀번호 correct 반응
                callback.setMonitor(3);
            }
            else {
                // 비밀번호 error 반응
                callback.error("비밀번호가 틀렸습니다.");
            }
        }
    }
}