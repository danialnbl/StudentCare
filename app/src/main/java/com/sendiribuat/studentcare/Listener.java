package com.sendiribuat.studentcare;

public class Listener {
    public String userid, fullName, age, phone, email, about;

    public Listener() {
    }

    public Listener(String userid, String fullName, String age, String phone, String email, String about) {
        this.userid = userid;
        this.fullName = fullName;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.about = about;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
