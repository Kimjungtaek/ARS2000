package com.example.a20190117;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Callback{
    Handler mHandler = new Handler();

    Log log;
    Password password;
    Value value;
    Monitor monitor;
    Setting setting;
    TimeMeasureTimer timeMeasureTimer;
    ButtonListener buttonListener;

    enum State{ready, running, pause}
    State state = State.ready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = new Value();
        monitor = new Monitor();
        setting = new Setting();
        log = new Log();
        password = new Password();
        timeMeasureTimer = new TimeMeasureTimer(R.id.timemeasure);
        buttonListener = new ButtonListener();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.mainframe, monitor)
                .add(R.id.mainframe, log)
                .add(R.id.mainframe, password)
                .add(R.id.mainframe, setting)
                .commit();
        setMonitor(0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasfocus) {
        super.onWindowFocusChanged(hasfocus);
        if(hasfocus){
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //KITKAT 이상 버전에서만
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                );
            }
        }
    }

    public void setMonitor(int n){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ImageButton monitorButton = findViewById(R.id.monitorButton);
        ImageButton logButton = findViewById(R.id.logButton);
        ImageButton settingButton = findViewById(R.id.settingButton);

        TextView monitorText = findViewById(R.id.monitortext);
        TextView logText = findViewById(R.id.logtext);
        TextView settingText = findViewById(R.id.settingtext);

        monitorButton.setBackgroundResource(R.drawable.round_pie_chart_unclicked);
        monitorText.setVisibility(View.INVISIBLE);
        logButton.setBackgroundResource(R.drawable.round_log_unclicked);
        logText.setVisibility(View.INVISIBLE);
        settingButton.setBackgroundResource(R.drawable.round_settings_unclicked);
        settingText.setVisibility(View.INVISIBLE);

        switch(n){
            case 0:
                monitorButton.setBackgroundResource(R.drawable.round_pie_chart_clicked);
                monitorText.setVisibility(View.VISIBLE);
                ft.show(monitor)
                        .hide(log)
                        .hide(password)
                        .hide(setting)
                        .commit();
                break;
            case 1:
                logButton.setBackgroundResource(R.drawable.round_log_clicked);
                logText.setVisibility(View.VISIBLE);
                ft.show(log)
                        .hide(monitor)
                        .hide(password)
                        .hide(setting)
                        .commit();
                break;
            case 2:
                settingButton.setBackgroundResource(R.drawable.round_settings_clicked);
                settingText.setVisibility(View.VISIBLE);
                ft.show(password)
                        .hide(monitor)
                        .hide(log)
                        .hide(setting)
                        .commit();
                break;
            case 3:
                settingButton.setBackgroundResource(R.drawable.round_settings_clicked);
                settingText.setVisibility(View.VISIBLE);
                ft.show(setting)
                        .hide(monitor)
                        .hide(log)
                        .hide(password)
                        .commit();
                break;
        }
    }

    //에러 표시 dialog
    public void error(String errorText) {
        //모두 정지
        timeMeasureTimer.pause();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Error")
                .setMessage("error : " + errorText)
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //다시 시작
                        timeMeasureTimer.resume();
                    }
                /*
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                */
                });
        alertDialogBuilder.show();
    }

    public void setValue(double xSetting, double ySetting, double zSetting, double temp){
        value.set(xSetting, ySetting, zSetting, temp);
        monitor.set(xSetting, ySetting, zSetting, temp);
    }

    //FG, BG, pause, cancel 버튼에 반응해서 상태 변경 및 실행
    private void changeState(State state){
        if(state == State.ready){
            Log.add('i', "main", "All process is stoped");
            //멈추었을 때 행동
            setting.setEditable(true);
            timeMeasureTimer.stop();
            this.state = State.ready;
        }
        else if(state == State.pause){
            Log.add('i', "main", "Process is paused");
            //일시정지 했을 때 행동
            timeMeasureTimer.pause();
            this.state = State.pause;
        }
        else if(timeMeasureTimer.isRunning()){
            Log.add('i', "main", "Process is resumed");
            //일시정지에서 계속 동작했을 때 행동
            timeMeasureTimer.resume();
            this.state = State.running;
        }
        else {
            Log.add('i', "main", "Process is started");
            //처음 시작했을 때 행동
            setting.setEditable(false);
            setting.dataToMain();
            timeMeasureTimer.start();
            this.state = State.running;
        }
    }

    class TimeMeasureTimer{
        View view;
        Timer mTimer;
        TextView stepTime, runTime;
        Date step, run, pause;

        boolean running = false;

        public TimeMeasureTimer(int id){
            view = (View) findViewById(id);
            stepTime = (TextView) view.findViewById(R.id.steptime);
            runTime = (TextView) view.findViewById(R.id.runtime);

            stepTime.setText("0");
            runTime.setText("0");
        }

        public void setstepStart(){
            stepTime.setText("0");
            step = new Date();
        }

        public void setrunStart(){
            runTime.setText("0");
            run = new Date();
        }

        public void start(){
            running = true;
            mTimer = new Timer();
            setstepStart();
            setrunStart();
            mTimer.schedule(new ThisTimerTask(), 10, 1000);
        }

        public void pause(){
            pause = new Date();
            mTimer.cancel();
        }

        public void resume(){
            Date present = new Date();
            step.setTime(step.getTime() + present.getTime() - pause.getTime());
            run.setTime(step.getTime() + present.getTime() - pause.getTime());
            mTimer = new Timer();
            mTimer.schedule(new ThisTimerTask(), 10, 1000);
        }

        public void stop(){
            running = false;
            mTimer.cancel();
        }

        public boolean isRunning(){
            return running;
        }

        class ThisTimerTask extends TimerTask{
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        Date rightNow = new Date();
                        long stepT = (rightNow.getTime() - step.getTime()) / 1000;
                        long runT = (rightNow.getTime() - step.getTime()) / 1000;

                        stepTime.setText(String.valueOf(stepT));
                        runTime.setText(String.valueOf(runT));
                    }
                });
            }
        }
    }

    class Value{
        View temper, height;
        TextView temperValue, heightValue;
        double xSetting, ySetting, zSetting, temp;

        public Value(){
            temper = (View) findViewById(R.id.temperature);
            View temperColorView = (View) temper.findViewById(R.id.colorview);
            temperColorView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            TextView temperTitle = (TextView) temper.findViewById(R.id.settingtitle);
            temperTitle.setText("Temperature");
            temperValue = (TextView) temper.findViewById(R.id.settingvalue);
            TextView temperUnit = (TextView) temper.findViewById(R.id.settingunit);
            temperUnit.setText("°C");

            height = (View) findViewById(R.id.disassemblyheight);
            View heightColorView = (View) height.findViewById(R.id.colorview);
            heightColorView.setBackgroundColor(getResources().getColor(R.color.colorGreenWhite));
            TextView heightTitle = (TextView) height.findViewById(R.id.settingtitle);
            heightTitle.setText("Disassembly Height");
            heightValue = (TextView) height.findViewById(R.id.settingvalue);
            TextView heightUnit = (TextView) height.findViewById(R.id.settingunit);
            heightUnit.setText("mm");
        }

        public void set(double xSetting, double ySetting, double zSetting, double temp){
            this.xSetting = xSetting;
            this.ySetting = ySetting;
            this.zSetting = zSetting;
            this.temp = temp;

            temperValue.setText(Double.toString(temp));
            heightValue.setText(Double.toString(zSetting));
        }
    }

    class ButtonListener implements Button.OnClickListener {
        ImageButton monitorButton, editButton, logButton;
        ImageButton inButton, outButton;
        Button aButton, bButton;

        public ButtonListener(){
            monitorButton = findViewById(R.id.monitorButton);
            monitorButton.setOnClickListener(this);
            logButton = findViewById(R.id.logButton);
            logButton.setOnClickListener(this);
            editButton = findViewById(R.id.settingButton);
            editButton.setOnClickListener(this);
            inButton = findViewById(R.id.inButton);
            inButton.setOnClickListener(this);
            outButton = findViewById(R.id.outButton);
            outButton.setOnClickListener(this);
            aButton = findViewById(R.id.buttonA);
            aButton.setOnClickListener(this);
            bButton = findViewById(R.id.buttonB);
            bButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            switch(view.getId()) {
                case R.id.monitorButton :
                    setMonitor(0);
                    break;
                case R.id.logButton :
                    setMonitor(1);
                    break;
                case R.id.settingButton :
                    setMonitor(2);
                    break;
                case R.id.inButton :
                    monitor.startSimul();
                    break;
                case R.id.outButton :
                    break;
                case R.id.buttonA :
                    buttonAonClick();
                    break;
                case R.id.buttonB :
                    buttonBonClick();
                    break;
            }
        }

        private void buttonAonClick(){
            if(state == State.ready){
                Log.add('i', "buttonA", "FG button is pushed");
                aButton.setText("");
                bButton.setText("");
                aButton.setBackgroundResource(R.drawable.round_pause_circle);
                bButton.setBackgroundResource(R.drawable.outline_cancel);
                changeState(State.running);
            } else if(state == State.running){
                Log.add('i', "buttonA", "Pause button is pushed");
                aButton.setText("");
                bButton.setText("");
                aButton.setBackgroundResource(R.drawable.outline_play_circle);
                bButton.setBackgroundResource(R.drawable.outline_cancel);
                changeState(State.pause);
            } else{
                Log.add('i', "buttonA", "Resume button is pushed");
                aButton.setText("");
                bButton.setText("");
                aButton.setBackgroundResource(R.drawable.round_pause_circle);
                bButton.setBackgroundResource(R.drawable.outline_cancel);
                changeState(State.running);
            }
        }

        private void buttonBonClick(){
            if(state == State.ready){
                Log.add('i', "buttonB", "BG button is pushed");
                aButton.setText("");
                bButton.setText("");
                aButton.setBackgroundResource(R.drawable.round_pause_circle);
                bButton.setBackgroundResource(R.drawable.outline_cancel);
                changeState(State.running);
            } else if(state == State.running){
                Log.add('i', "buttonB", "Stop button is pushed");
                aButton.setText("FG");
                bButton.setText("Bg");
                aButton.setBackgroundResource(R.drawable.baseline);
                bButton.setBackgroundResource(R.drawable.baseline);
                changeState(State.ready);
            } else{
                Log.add('i', "buttonB", "Stop button is pushed");
                aButton.setText("FG");
                bButton.setText("Bg");
                aButton.setBackgroundResource(R.drawable.baseline);
                bButton.setBackgroundResource(R.drawable.baseline);
                changeState(State.ready);
            }
        }
    }

}
