package com.pg.helpdesk.entity;

import io.springlets.format.EntityFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@EntityFormat("#{name}")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    public Role(String name) {
        this.name = name;
    }
}
