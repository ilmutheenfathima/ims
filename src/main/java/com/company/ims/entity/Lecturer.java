package com.company.ims.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.List;

@JmixEntity
@Entity
public class Lecturer extends User {

    @Column(name = "LECTURER_ID")
    private String lecturerId;

    @Column(name = "ADDRESS")
    private String address;

    @JoinColumn(name = "MODULE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Module module;

    @OneToMany(mappedBy = "lecturer")
    private List<Classroom> classrooms;

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}