package com.company.ims.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@JmixEntity
@Entity
public class Lecturer extends User {

    @Column(name = "LECTURER_ID")
    private String lecturerId;

    @Column(name = "ADDRESS")
    private String address;

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}