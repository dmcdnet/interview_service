package com.dm.interview_service.model;

import java.util.ArrayList;
import java.util.List;

public class Interview {

    private List<InterviewContainer> nodes;

    public Interview(List<InterviewContainer> nodes) {
        this.nodes = nodes;
    }

    public List<InterviewContainer> getNodes() {
        return nodes;
    }

    public void setNodes(List<InterviewContainer> nodes) {
        this.nodes = nodes;
    }
}
