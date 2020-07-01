package com.unity.model;

public class TimeVO {
    int id;
    int time;
    int pretime;
    int accTime;

    public int getAccTime() {
        return accTime;
    }

    public void setAccTime(int accTime) {
        this.accTime = accTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPretime() {
        return pretime;
    }

    public void setPretime(int pretime) {
        this.pretime = pretime;
    }
}
