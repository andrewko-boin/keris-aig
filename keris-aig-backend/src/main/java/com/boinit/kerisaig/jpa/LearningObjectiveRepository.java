package com.boinit.kerisaig.jpa;

import com.boinit.kerisaig.repository.entity.LearningObjective;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningObjectiveRepository extends JpaRepository<LearningObjective, Integer> {
    public Page<LearningObjective> findByGuno(Pageable pageable, int guno);
}