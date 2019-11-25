package com.boinit.kerisaig.jpa;

import com.boinit.kerisaig.repository.entity.GradeUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeUnitRepository extends JpaRepository<GradeUnit, Integer> {
}