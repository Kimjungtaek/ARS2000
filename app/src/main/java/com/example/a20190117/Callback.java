package com.example.a20190117;

public interface Callback {
    void setMonitor(int n);
    void error(String errortext);
    void setValue(double h, double t, double d);
}
