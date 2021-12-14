package com.dm.interview_service.model;

public class Answer extends InterviewNode {

    private String answer;

    public Answer(String id, InterviewNode parent, String answer) {
        super(id, parent);
        this.answer = answer;
    }

    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public InterviewNode childExists(String id) {
        return null;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
