package com.boinit.kerisaig.service;

import com.boinit.kerisaig.repository.entity.Question;

public interface QuestionConsumer {
    public void detectionQuestion(Question question);

    public void generationQuestion(Question question);
}