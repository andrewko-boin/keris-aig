package com.boinit.kerisaig.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GRADE_UNIT")
public class GradeUnit implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3423961242822117508L;

    @Column(name = "GUNO")
    @Id
    private int guno;

    @Column(name = "EDU_GUBN")
    private String eduGubn;

    @Column(name = "SCHOOL_YEAR")
    private String schoolYear;

    @Column(name = "SEMESTER")
    private int semester;

    @Column(name = "UNIT")
    private Integer unit;

    @Column(name = "UNIT_VALUE")
    private String unitValue;

    public int getGuno() {
        return guno;
    }

    public void setGuno(int guno) {
        this.guno = guno;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getEduGubn() {
        return eduGubn;
    }

    public void setEduGubn(String eduGubn) {
        this.eduGubn = eduGubn;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitName(String unitValue) {
        this.unitValue = unitValue;
    }
}