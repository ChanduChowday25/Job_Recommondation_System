package com.jobportal.controller;

import com.jobportal.model.Candidate;
import com.jobportal.model.Skill;
import com.jobportal.model.CandidateSkill;
import com.jobportal.repository.CandidateRepository;
import com.jobportal.repository.SkillRepository;
import com.jobportal.repository.CandidateSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate-skill")
public class CandidateSkillController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @PostMapping
    public CandidateSkill assignSkillToCandidate(@RequestParam Long candidateId, @RequestParam Long skillId) {

        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        Skill skill = skillRepository.findById(skillId).orElse(null);

        CandidateSkill cs = new CandidateSkill();
        cs.setCandidate(candidate);
        cs.setSkill(skill);

        return candidateSkillRepository.save(cs);
    }
}
