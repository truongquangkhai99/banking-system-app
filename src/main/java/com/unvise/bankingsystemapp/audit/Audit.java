package com.unvise.bankingsystemapp.audit;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;

@Embeddable
@Data
public class Audit {

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    public void create() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    public void update() {
        updatedAt = LocalDate.now();
    }

}
