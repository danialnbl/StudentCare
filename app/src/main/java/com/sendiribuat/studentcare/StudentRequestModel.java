package com.sendiribuat.studentcare;

public class StudentRequestModel
{
    String age,date,fullName,time, userid,email;

    StudentRequestModel(){
    }

    public StudentRequestModel(String age, String date, String fullName, String time, String userid, String email) {
        this.age = age;
        this.date = date;
        this.fullName = fullName;
        this.time = time;
        this.userid = userid;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
