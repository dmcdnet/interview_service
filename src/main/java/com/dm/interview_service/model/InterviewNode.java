package com.dm.interview_service.model;

public abstract class InterviewNode {

    private String id;
    private Split split;

    public InterviewNode() {}

    public InterviewNode(String id) {
        this.id = id;
    }

    public InterviewNode(String id, Split split) {
        this.id = id;
        this.split = split;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Split getSplit() {
        return split;
    }

    public void setSplit(Split split) {
        this.split = split;
    }
}
