package com.Appathon.Covid;

import java.util.LinkedList;

public class ChartData {
    public LinkedList<String> getLabels() {
        return labels;
    }

    public void setLabels(LinkedList<String> labels) {
        this.labels = labels;
    }

    public LinkedList<DataSet> getDatasets() {
        return datasets;
    }

    public void setDatasets(LinkedList<DataSet> datasets) {
        this.datasets = datasets;
    }

    private LinkedList<String> labels;

    private LinkedList<DataSet> datasets;

    public ChartData(LinkedList<String> labels, LinkedList<DataSet> datasets) {
        this.labels = labels;
        this.datasets = datasets;
    }
}
