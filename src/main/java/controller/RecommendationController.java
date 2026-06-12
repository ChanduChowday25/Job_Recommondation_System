package com.jobportal.controller;

import com.jobportal.model.*;
import com.jobportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @GetMapping("/{candidateId}")
    public List<Map<String, Object>> recommendJobs(@PathVariable Long candidateId) {

        // 1) Get candidate
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // 2) Get candidate skills (as names)
        List<String> candidateSkills = candidateSkillRepository.findAll().stream()
                .filter(cs -> cs.getCandidate().getId().equals(candidateId))
                .map(cs -> cs.getSkill().getName())
                .collect(Collectors.toList());

        // 3) For each job, compute match %
        List<Map<String, Object>> result = new ArrayList<>();

        for (Job job : jobRepository.findAll()) {

            List<String> jobSkills = jobSkillRepository.findAll().stream()
                    .filter(js -> js.getJob().getId().equals(job.getId()))
                    .map(js -> js.getSkill().getName())
                    .collect(Collectors.toList());

            if (jobSkills.isEmpty()) continue;

            int match = 0;
            for (String skill : jobSkills) {
                if (candidateSkills.contains(skill)) {
                    match++;
                }
            }

            double matchPercent = (double) match / jobSkills.size() * 100;

            Map<String, Object> map = new HashMap<>();
            map.put("jobId", job.getId());
            map.put("title", job.getTitle());
            map.put("matchPercentage", matchPercent);

            result.add(map);
        }

        // 4) Sort by best match
        result.sort((a, b) -> Double.compare(
                (double) b.get("matchPercentage"),
                (double) a.get("matchPercentage"))
        );

        return result;
    }
}
