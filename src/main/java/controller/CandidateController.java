package com.jobportal.controller;

import com.jobportal.model.Candidate;
import com.jobportal.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    // POST → create candidate
    @PostMapping
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    // GET → list candidates
    @GetMapping
    public List<Candidate> getCandidates() {
        return candidateRepository.findAll();
    }
}
