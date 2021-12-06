package com.dm.interview_service.definition;

import java.util.List;
import java.util.Objects;

public class QuestionDefinition {

    private List<ContainerDefinition> path;
    private String id;
    private String question;
    private String splitDefinition;
    private String condition;

    public QuestionDefinition() {}

    public QuestionDefinition(List<ContainerDefinition> path, String id, String question, String splitDefinition, String condition) {
        this.path = path;
        this.id = id;
        this.question = question;
        this.splitDefinition = splitDefinition;
        this.condition = condition;
    }

    public List<ContainerDefinition> getPath() {
        return path;
    }

    public void setPath(List<ContainerDefinition> path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSplitDefinition() {
        return splitDefinition;
    }

    public void setSplitDefinition(String splitDefinition) {
        this.splitDefinition = splitDefinition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDefinition that = (QuestionDefinition) o;
        return Objects.equals(path, that.path) && Objects.equals(id, that.id) && Objects.equals(question, that.question) && Objects.equals(splitDefinition, that.splitDefinition) && Objects.equals(condition, that.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, id, question, splitDefinition, condition);
    }

    @Override
    public String toString() {
        return "QuestionDefinition{" +
                "path=" + path +
                ", id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", splitDefinition='" + splitDefinition + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
