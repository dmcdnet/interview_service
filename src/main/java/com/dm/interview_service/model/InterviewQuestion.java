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


}
