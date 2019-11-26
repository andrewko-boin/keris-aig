package com.boinit.kerisaig.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LEARNING_OBJECTIVES")
public class LearningObjective implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8948840348015686175L;

    @Column(name = "LBNO")
    @Id
    private int lbno;

    @Column(name = "GUNO")
    private int guno;

    @Column(name = "OBJECTIVE")
    private String objective;

    @Column(name = "LESSON")
    private String lesson;

    public int getLbno() {
        return lbno;
    }

    public void setLbno(int lbno) {
        this.lbno = lbno;
    }

    public int getGuno() {
        return guno;
    }

    public void setGuno(int guno) {
        this.guno = guno;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

}