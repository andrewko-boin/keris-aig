package com.boinit.kerisaig.service;

import com.boinit.kerisaig.repository.entity.Question;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuestionConsumerImpl implements QuestionConsumer {

    private RestTemplate restTemplate;

    @Override
    public void detectionQuestion(Question question) {
        // TODO Auto-generated method stub

    }

    @Override
    public void generationQuestion(Question question) {
        // TODO Auto-generated method stub

    }

}