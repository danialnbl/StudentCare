package com.sendiribuat.studentcare;

public class Student {

    public String fullName, age, email, phone, userType;

    public Student(){

    }

    public Student(String fullName, String age, String email,String phone ,String userType){
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
    }
}
