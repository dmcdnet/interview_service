package com.dm.interview_service.model;

import java.util.HashSet;
import java.util.Set;

public class InterviewQuestion extends InterviewNode {

    private String question;
    private Set<Answer> answers;

    public InterviewQuestion(String id, InterviewNode parent, String question, Set<Answer> answers) {
        super(id, parent);
        this.question = question;
        this.answers = answers;
    }

    public InterviewQuestion(String id, InterviewNode parent, Split split, String question, Set<Answer> answers) {
        super(id, parent, split);
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
    public InterviewNode split(boolean splitRoot) {
        if( this.getSplit() != null && !this.getSplit().isSplit() && !splitRoot ) {
            // above has been split, we can be split but haven't yet
            return new InterviewQuestion(this.getId(), this.getParent(), this.getSplit().clone(), this.getQuestion(), new HashSet<>());
        } if( this.getSplit() == null && !splitRoot ) {
            // above has been split, we can't be split but group or clause can
            return new InterviewQuestion(this.getId(), this.getParent(), this.getQuestion(), new HashSet<>());
        } else if( this.getSplit() != null && !this.getSplit().isSplit() && splitRoot ) {
            // we can be split and are the split root
            return new InterviewQuestion(this.getId(), this.getParent(), this.getQuestion(), new HashSet<>());
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "InterviewQuestion{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                "} " + super.toString();
    }
}
