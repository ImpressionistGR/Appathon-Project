package com.Appathon.Covid;

import java.util.LinkedList;

public class DataSet {
    private String label;

    private LinkedList<Integer> data;

    private LinkedList<String> backgroundColor;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LinkedList<Integer> getData() {
        return data;
    }

    public void setData(LinkedList<Integer> data) {
        this.data = data;
    }

    public LinkedList<String> getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(LinkedList<String> backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public DataSet(String label, LinkedList<Integer> data, LinkedList<String> backgroundColor) {
        this.label = label;
        this.data = data;
        String str = "";

        //generate random backgroundColor for each data
        for(int i=0;i<data.size(); i++) {
            System.out.println(data.size());
            str = "rgba("+ (int)(Math.random() * (256)) + ", " +
                  (int)(Math.random() * (256)) + ", " +
                  (int)(Math.random() * (256)) + ", " +
                  (int)(Math.random() * (256)) + ")";
            System.out.println(str);
            backgroundColor.add(str);
        }
        this.backgroundColor = backgroundColor;
    }
}
