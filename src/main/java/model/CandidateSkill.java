package com.jobportal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CandidateSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Candidate candidate;

    @ManyToOne
    private Skill skill;
}
