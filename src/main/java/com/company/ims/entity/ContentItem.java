package com.company.ims.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
            joinColumns = @JoinColumn(name = "CONTENT_ITEM_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLASSROOM_ID"))
    @ManyToMany
    private List<Classroom> visibleClassrooms;

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