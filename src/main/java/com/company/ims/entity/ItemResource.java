package com.company.ims.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "ITEM_RESOURCE", indexes = {
        @Index(name = "IDX_ITEM_RESOURCE_CONTENT_ITEM", columnList = "CONTENT_ITEM_ID")
})
@Entity
public class ItemResource {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "FILE_", nullable = false, length = 1024)
    @NotNull
    private FileRef file;

    @JoinColumn(name = "CONTENT_ITEM_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ContentItem contentItem;

    public ContentItem getContentItem() {
        return contentItem;
    }

    public void setContentItem(ContentItem contentItem) {
        this.contentItem = contentItem;
    }

    public FileRef getFile() {
        return file;
    }

    public void setFile(FileRef file) {
        this.file = file;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}