package com.company.ims.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@JmixEntity
@Entity
public class Lecturer extends User {

    @Column(name = "LECTURER_ID")
    private Integer lecturerId;

    @Column(name = "ADDRESS")
    private String address;

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}