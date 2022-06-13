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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
