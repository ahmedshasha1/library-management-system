package com.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class ChildBaseEntity extends BaseEntity{
    @Column(nullable = false)
    private String name;
}
