package com.dm.interview_service.model;

import java.util.ArrayList;
import java.util.List;

public class InterviewContainer extends InterviewNode{

    private List<InterviewNode> nodes;

    public InterviewContainer() {
        super("");
        this.nodes = new ArrayList<>();
    }

    public InterviewContainer(String id, List<InterviewNode> nodes) {
        super(id);
        this.nodes = nodes;
    }

    public InterviewContainer(String id, Split split, List<InterviewNode> nodes) {
        super(id, split);
        this.nodes = nodes;
    }

    public List<InterviewNode> getNodes() {
        return nodes;
    }

    @Override
    public InterviewNode childExists(String id) {
        return nodes.stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "InterviewContainer{" +
                "nodes=" + nodes +
                "} " + super.toString();
    }
}
