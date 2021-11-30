package com.dm.interview_service.model;

import java.util.List;

public class InterviewContainer extends InterviewNode{

    private List<InterviewNode> nodes;

    public InterviewContainer(String id, List<InterviewNode> nodes) {
        super(id);
        this.nodes = nodes;
    }

    public InterviewContainer(String id, Split split, List<InterviewNode> nodes) {
        super(id, split);
        this.nodes = nodes;
    }
}
