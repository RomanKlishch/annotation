package com.rk.reflection;

import com.rk.annotation.Inject;
import com.rk.annotation.Run;

import java.util.ArrayList;
import java.util.List;


public class AppTest {

    private int number;
    private String stringPrivate;
    @Inject
    public String stringPublic;
    @Inject(ArrayList.class)
    private List<String> stringList;

    public AppTest() {
    }

    public AppTest(int number, String stringPrivate, String stringPublic) {
        this.number = number;
        this.stringPrivate = stringPrivate;
        this.stringPublic = stringPublic;
    }

    public int getNumber() {
        return number;
    }

    public String getStringPrivate() {
        return stringPrivate;
    }

    public String getStringPublic() {
        return stringPublic;
    }

    public List<String> getStringList() {
        return stringList;
    }

    @Run
    public void setNumber() {
        this.number = 10;
    }

    @Run
    public void setStringPrivate() {
        this.stringPrivate = "worked";
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void setStringPublic(String stringPublic) {
        this.stringPublic = stringPublic;
    }
}
