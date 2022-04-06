package com.sendiribuat.studentcare;

public class model {
    String age,email,fullName;

    model(){

    }

    public model(String age, String email, String fullName) {
        this.age = age;
        this.email = email;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
