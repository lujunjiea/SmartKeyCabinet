package com.example.smartkeycabinet.model;

import java.io.Serializable;

public class GetCarModel implements Serializable {

    /**
     * identificationMethod : 取车码
     * operator : 王五
     * deptId : 1
     * deptName : 测试部门
     * phone : 15066858654
     * vehiclePickupCode : 123456
     */
    private String identificationMethod;
    private String operator;
    private String deptId;
    private String deptName;
    private String phone;
    private String vehiclePickupCode;

    public GetCarModel(String identificationMethod, String operator, String deptId, String deptName, String phone, String vehiclePickupCode) {
        this.identificationMethod = identificationMethod;
        this.operator = operator;
        this.deptId = deptId;
        this.deptName = deptName;
        this.phone = phone;
        this.vehiclePickupCode = vehiclePickupCode;
    }

    public String getIdentificationMethod() {
        return identificationMethod;
    }

    public void setIdentificationMethod(String identificationMethod) {
        this.identificationMethod = identificationMethod;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehiclePickupCode() {
        return vehiclePickupCode;
    }

    public void setVehiclePickupCode(String vehiclePickupCode) {
        this.vehiclePickupCode = vehiclePickupCode;
    }

    @Override
    public String toString() {
        return "GetCarModel{" +
                "identificationMethod='" + identificationMethod + '\'' +
                ", operator='" + operator + '\'' +
                ", deptId='" + deptId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", phone='" + phone + '\'' +
                ", vehiclePickupCode='" + vehiclePickupCode + '\'' +
                '}';
    }
}
