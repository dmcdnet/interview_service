package com.dm.interview_service.model;

import java.util.Set;

public class InterviewQuestion extends InterviewNode {

    private String question;
    private Set<Answer> answers;

    public InterviewQuestion(String id, String question, Set<Answer> answers) {
        super(id);
        this.question = question;
        this.answers = answers;
    }

    public InterviewQuestion(String id, Split split, String question, Set<Answer> answers) {
        super(id, split);
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public InterviewNode childExists(String id) {
        return answers.stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "InterviewQuestion{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                "} " + super.toString();
    }
}
