package com.dm.interview_service.definition;

import java.util.List;
import java.util.Objects;

public class CaptureDefinition {

    private List<QuestionDefinition> questions;
    private String id;
    private String name;

    public CaptureDefinition(List<QuestionDefinition> questions, String id, String name) {
        this.questions = questions;
        this.id = id;
        this.name = name;
    }

    public CaptureDefinition() {
    }

    public List<QuestionDefinition> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDefinition> questions) {
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaptureDefinition that = (CaptureDefinition) o;
        return Objects.equals(questions, that.questions) && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questions, id, name);
    }

    @Override
    public String toString() {
        return "CaptureDefinition{" +
                "questions=" + questions +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
