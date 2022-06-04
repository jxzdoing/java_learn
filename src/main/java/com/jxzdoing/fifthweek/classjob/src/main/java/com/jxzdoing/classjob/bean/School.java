package com.jxzdoing.classjob.bean;

import java.util.List;

public class School {

    private List<MyClass> myClasses;

    public List<MyClass> getMyClasses() {
        return myClasses;
    }

    public void setMyClasses(List<MyClass> myClasses) {
        this.myClasses = myClasses;
    }

    @Override
    public String toString() {
        return "MyClass::" + myClasses.toString();
    }
}
