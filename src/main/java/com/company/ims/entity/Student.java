package com.company.ims.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@JmixEntity
@Entity
public class Student extends User {
    @Column(name = "STUDENT_ID")
    private Integer studentId;

    @Column(name = "ADDRESS")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}