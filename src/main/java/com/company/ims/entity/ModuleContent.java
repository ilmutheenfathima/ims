package com.company.ims.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "MODULE_CONTENT", indexes = {
        @Index(name = "IDX_MODULECONTENT_INTAKEMODULE", columnList = "INTAKE_MODULE_ID")
})
@Entity
public class ModuleContent {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "TITLE")
    private String title;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @JoinColumn(name = "INTAKE_MODULE_ID", nullable = false)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private IntakeModule intakeModule;

    @Column(name = "MODULE_FEE")
    private Double moduleFee;

    @Column(name = "PAYMENT_OPTIONS")
    @Lob
    private String paymentOptions;

    @Composition
    @OneToMany(mappedBy = "content")
    private List<ContentItem> items;

    public String getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(String paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    public Double getModuleFee() {
        return moduleFee;
    }

    public void setModuleFee(Double moduleFee) {
        this.moduleFee = moduleFee;
    }

    public List<ContentItem> getItems() {
        return items;
    }

    public void setItems(List<ContentItem> items) {
        this.items = items;
    }

    public IntakeModule getIntakeModule() {
        return intakeModule;
    }

    public void setIntakeModule(IntakeModule intakeModule) {
        this.intakeModule = intakeModule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}