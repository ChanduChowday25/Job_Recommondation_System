package com.jobportal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JobSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Job job;

    @ManyToOne
    private Skill skill;
}