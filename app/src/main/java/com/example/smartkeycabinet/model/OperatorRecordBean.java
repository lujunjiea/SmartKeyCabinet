package com.example.smartkeycabinet.model;


import java.io.Serializable;
import java.util.List;

public class OperatorRecordBean implements Serializable {
    private String total;

    private List<OperatorRecordModel> list;

    public OperatorRecordBean(String total, List<OperatorRecordModel> list) {
        this.total = total;
        this.list = list;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OperatorRecordModel> getList() {
        return list;
    }

    public void setList(List<OperatorRecordModel> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "OperatorRecordBean{" +
                "total='" + total + '\'' +
                ", list=" + list +
                '}';
    }
}
