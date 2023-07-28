package com.company.ims.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "CONTENT_ITEM", indexes = {
        @Index(name = "IDX_CONTENT_ITEM_CONTENT", columnList = "CONTENT_ID")
})
@Entity
public class ContentItem {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @JoinColumn(name = "CONTENT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ModuleContent content;

    @JoinTable(name = "CONTENT_ITEM_CLASSROOM_LINK",
            joinColumns = @JoinColumn(name = "CONTENT_ITEM_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CLASSROOM_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Classroom> visibleClassrooms;

    @Composition
    @OneToMany(mappedBy = "contentItem")
    private List<ItemResource> resources;

    @OneToMany(mappedBy = "contentItem")
    private List<Submission> submissions;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public List<ItemResource> getResources() {
        return resources;
    }

    public void setResources(List<ItemResource> resources) {
        this.resources = resources;
    }

    public List<Classroom> getVisibleClassrooms() {
        return visibleClassrooms;
    }

    public void setVisibleClassrooms(List<Classroom> visibleClassrooms) {
        this.visibleClassrooms = visibleClassrooms;
    }

    public ModuleContent getContent() {
        return content;
    }

    public void setContent(ModuleContent content) {
        this.content = content;
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