package com.company.ims.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "CLASSROOM", indexes = {
        @Index(name = "IDX_CLASSROOM_LECTURER", columnList = "LECTURER_ID"),
        @Index(name = "IDX_CLASSROOM_BATCH", columnList = "BATCH_ID"),
        @Index(name = "IDX_CLASSROOM_INTAKE_MODULE", columnList = "INTAKE_MODULE_ID")
})
@Entity
public class Classroom {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @JoinColumn(name = "BATCH_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Batch batch;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "DAY_")
    private String day;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @JoinColumn(name = "LECTURER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lecturer lecturer;

    @JoinColumn(name = "INTAKE_MODULE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private IntakeModule intakeModule;

    @JoinTable(name = "CONTENT_ITEM_CLASSROOM_LINK",
            joinColumns = @JoinColumn(name = "CLASSROOM_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTENT_ITEM_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ContentItem> visibleContentItems;

    @OneToMany(mappedBy = "classroom")
    private List<Enrolment> enrolments;

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void setEnrolments(List<Enrolment> enrolments) {
        this.enrolments = enrolments;
    }

    public List<ContentItem> getVisibleContentItems() {
        return visibleContentItems;
    }

    public void setVisibleContentItems(List<ContentItem> visibleContentItems) {
        this.visibleContentItems = visibleContentItems;
    }

    public IntakeModule getIntakeModule() {
        return intakeModule;
    }

    public void setIntakeModule(IntakeModule intakeModule) {
        this.intakeModule = intakeModule;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdentifiableName() {
        return name + " [Batch: " + batch.getName() + "; Lecturer: " + lecturer.getFullName() + "]";
    }
}