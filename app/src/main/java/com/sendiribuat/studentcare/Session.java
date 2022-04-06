package com.sendiribuat.studentcare;

public class Session {
    public String fullName, age, date, time, userid;

    public Session(){

    }

    public Session(String fullName, String age, String date, String time, String userid){
        this.fullName = fullName;
        this.age = age;
        this.date = date;
        this.time = time;
        this.userid = userid;
    }
}
