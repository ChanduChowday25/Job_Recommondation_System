package com.jobportal.controller;

import com.jobportal.model.Job;
import com.jobportal.model.Skill;
import com.jobportal.model.JobSkill;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.SkillRepository;
import com.jobportal.repository.JobSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-skill")
public class JobSkillController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @PostMapping("/assign")
    public JobSkill assignSkillToJob(@RequestParam Long jobId,
                                     @RequestParam Long skillId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        JobSkill jobSkill = new JobSkill();
        jobSkill.setJob(job);
        jobSkill.setSkill(skill);

        return jobSkillRepository.save(jobSkill);
    }
}