package com.dm.interview_service.model;

import java.util.ArrayList;
import java.util.List;

public class Interview {

    private List<InterviewNode> nodes = new ArrayList<>();

    public Interview(List<InterviewNode> nodes) {
        this.nodes = nodes;
    }

    public List<InterviewNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<InterviewNode> nodes) {
        this.nodes = nodes;
    }
}
