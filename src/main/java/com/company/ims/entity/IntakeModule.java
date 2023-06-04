package com.company.ims.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "INTAKE_MODULE", indexes = {
        @Index(name = "IDX_INTAKE_MODULE_INTAKE", columnList = "INTAKE_ID"),
        @Index(name = "IDX_INTAKE_MODULE_MODULE", columnList = "MODULE_ID")
})
@Entity
public class IntakeModule {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @JoinColumn(name = "INTAKE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Intake intake;

    @OneToMany(mappedBy = "intakeModule")
    private List<Classroom> classrooms;

    @JoinColumn(name = "MODULE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Module module;

    @JoinColumn(name = "MODULE_CONTENT_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private ModuleContent moduleContent;

    @OneToMany(mappedBy = "intakeModule")
    private List<Enrolment> enrolments;

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void setEnrolments(List<Enrolment> enrolments) {
        this.enrolments = enrolments;
    }

    public ModuleContent getModuleContent() {
        return moduleContent;
    }

    public void setModuleContent(ModuleContent moduleContent) {
        this.moduleContent = moduleContent;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public List<Classroom> getClasses() {
        return classrooms;
    }

    public void setClasses(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Intake getIntake() {
        return intake;
    }

    public void setIntake(Intake intake) {
        this.intake = intake;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    public String getModuleName() {
        return module.getName();
    }
}