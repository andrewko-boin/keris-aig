package com.boinit.kerisaig.jpa;

import java.util.Optional;

import com.boinit.kerisaig.repository.entity.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    public Page<Question> findByLbno(Pageable pageable, int lbno);

    public Optional<Question> findByQsno(int qsno);
}