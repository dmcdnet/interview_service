package com.dm.interview_service.model;

public abstract class InterviewNode {

    private String id;
    private Split split;

    public InterviewNode(String id) {
        this.id = id;
    }

    public InterviewNode(String id, Split split) {
        this.id = id;
        this.split = split;
    }
}
