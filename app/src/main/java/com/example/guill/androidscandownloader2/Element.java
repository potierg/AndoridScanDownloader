package com.example.guill.androidscandownloader2;

/**
 * Created by guill on 30/04/2017.
 */

public class Element {
    public String name;
    public String path;
    public Boolean is_file;

    public Element(String name, String path, Boolean is_file)
    {
        this.name = name;
        this.path = path;
        this.is_file = is_file;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Boolean getIs_file() {
        return is_file;
    }
}
