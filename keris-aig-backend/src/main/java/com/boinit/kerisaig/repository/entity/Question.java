package com.boinit.kerisaig.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTIONS")
public class Question implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3282196495330420672L;

    @Column(name = "QSNO")
    @Id
    private int qsno;

    @Column(name = "LBNO")
    private int lbno;

    @Column(name = "QUESTION_TYPE")
    private String questionType;

    @Column(name = "DIFFICULTY")
    private String defficulty;

    @Column(name = "CHAPTER_CODE")
    private String chapterCode;

    @Column(name = "CATEGORY1")
    private String category1;

    @Column(name = "CATEGORY2")
    private String category2;

    @Column(name = "CATEGORY3")
    private String category3;

    @Column(name = "CATEGORY4")
    private String category4;

    @Column(name = "CATEGORY5")
    private String category5;

    @Column(name = "ETC_CATEGORY1")
    private String etcCategory1;

    @Column(name = "ETC_CATEGORY2")
    private String etcCategory2;

    @Column(name = "ETC_CATEGORY3")
    private String etcCategory3;

    @Column(name = "ETC_CATEGORY4")
    private String etcCategory4;

    @Column(name = "ETC_CATEGORY5")
    private String etcCategory5;

    @Column(name = "EXT_FEILD14")
    private String extfeild14;

    @Column(name = "EXT_FEILD15")
    private String extfeild15;

    @Column(name = "EXT_FEILD16")
    private String extfeild16;

    @Column(name = "EXT_FEILD17")
    private String extfeild17;

    @Column(name = "EXT_FEILD18")
    private String extfeild18;

    @Column(name = "EXT_FEILD19")
    private String extfeild19;

    @Column(name = "EXT_FEILD20")
    private String extfeild20;

    @Column(name = "BODY_HTML")
    @Lob
    private String bodyhtml;

    @Column(name = "LIST_HTML")
    @Lob
    private String listhtml;

    @Column(name = "ANSWER_HTML")
    @Lob
    private String answerhtml;

    @Column(name = "BODY_EXT")
    @Lob
    private String bodyext;

    public int getQsno() {
        return qsno;
    }

    public void setQsno(int qsno) {
        this.qsno = qsno;
    }

    public int getLbno() {
        return lbno;
    }

    public void setLbno(int lbno) {
        this.lbno = lbno;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getDefficulty() {
        return defficulty;
    }

    public void setDefficulty(String defficulty) {
        this.defficulty = defficulty;
    }

    public String getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(String chapterCode) {
        this.chapterCode = chapterCode;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public String getCategory4() {
        return category4;
    }

    public void setCategory4(String category4) {
        this.category4 = category4;
    }

    public String getCategory5() {
        return category5;
    }

    public void setCategory5(String category5) {
        this.category5 = category5;
    }

    public String getEtcCategory1() {
        return etcCategory1;
    }

    public void setEtcCategory1(String etcCategory1) {
        this.etcCategory1 = etcCategory1;
    }

    public String getEtcCategory2() {
        return etcCategory2;
    }

    public void setEtcCategory2(String etcCategory2) {
        this.etcCategory2 = etcCategory2;
    }

    public String getEtcCategory3() {
        return etcCategory3;
    }

    public void setEtcCategory3(String etcCategory3) {
        this.etcCategory3 = etcCategory3;
    }

    public String getEtcCategory4() {
        return etcCategory4;
    }

    public void setEtcCategory4(String etcCategory4) {
        this.etcCategory4 = etcCategory4;
    }

    public String getEtcCategory5() {
        return etcCategory5;
    }

    public void setEtcCategory5(String etcCategory5) {
        this.etcCategory5 = etcCategory5;
    }

    public String getExtfeild14() {
        return extfeild14;
    }

    public void setExtfeild14(String extfeild14) {
        this.extfeild14 = extfeild14;
    }

    public String getExtfeild15() {
        return extfeild15;
    }

    public void setExtfeild15(String extfeild15) {
        this.extfeild15 = extfeild15;
    }

    public String getExtfeild16() {
        return extfeild16;
    }

    public void setExtfeild16(String extfeild16) {
        this.extfeild16 = extfeild16;
    }

    public String getExtfeild17() {
        return extfeild17;
    }

    public void setExtfeild17(String extfeild17) {
        this.extfeild17 = extfeild17;
    }

    public String getExtfeild18() {
        return extfeild18;
    }

    public void setExtfeild18(String extfeild18) {
        this.extfeild18 = extfeild18;
    }

    public String getExtfeild19() {
        return extfeild19;
    }

    public void setExtfeild19(String extfeild19) {
        this.extfeild19 = extfeild19;
    }

    public String getExtfeild20() {
        return extfeild20;
    }

    public void setExtfeild20(String extfeild20) {
        this.extfeild20 = extfeild20;
    }

    public String getBodyhtml() {
        return bodyhtml;
    }

    public void setBodyhtml(String bodyhtml) {
        this.bodyhtml = bodyhtml;
    }

    public String getListhtml() {
        return listhtml;
    }

    public void setListhtml(String listhtml) {
        this.listhtml = listhtml;
    }

    public String getAnswerhtml() {
        return answerhtml;
    }

    public void setAnswerhtml(String answerhtml) {
        this.answerhtml = answerhtml;
    }

    public String getBodyext() {
        return bodyext;
    }

    public void setBodyext(String bodyext) {
        this.bodyext = bodyext;
    }
}