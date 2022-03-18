package com.sendiribuat.studentcare;

public class model {
    String age,caseType,email,fullName;

    model(){

    }

    public model(String age, String caseType, String email, String fullName) {
        this.age = age;
        this.caseType = caseType;
        this.email = email;
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
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
