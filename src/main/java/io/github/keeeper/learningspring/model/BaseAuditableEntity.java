package io.github.keeeper.learningspring.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
abstract class BaseAuditableEntity {

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    public void prePersist(){
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preMerge(){
        updatedOn = LocalDateTime.now();
    }
}
