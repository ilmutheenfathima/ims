package com.company.ims.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "SUBMISSION", indexes = {
        @Index(name = "IDX_SUBMISSION_STUDENT", columnList = "STUDENT_ID"),
        @Index(name = "IDX_SUBMISSION_CONTENT_ITEM", columnList = "CONTENT_ITEM_ID")
})
@Entity
public class Submission {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "TEXT", nullable = false)
    @Lob
    @NotNull
    private String text;

    @Column(name = "FILE_", length = 1024)
    private FileRef file;

    @Column(name = "MARKS")
    private Float marks;

    @JoinColumn(name = "STUDENT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Student student;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Float getMarks() {
        return marks;
    }

    public void setMarks(Float marks) {
        this.marks = marks;
    }

    public FileRef getFile() {
        return file;
    }

    public void setFile(FileRef file) {
        this.file = file;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}