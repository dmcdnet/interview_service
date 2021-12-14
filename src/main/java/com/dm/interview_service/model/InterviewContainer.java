package com.dm.interview_service.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterviewContainer extends InterviewNode{

    private List<InterviewNode> nodes;

    public InterviewContainer() {
        super("", null);
        this.nodes = new ArrayList<>();
    }

    public InterviewContainer(String id, InterviewNode parent, List<InterviewNode> nodes) {
        super(id, parent);
        this.nodes = nodes;
        refreshNodePathCache(this.getNodes());
    }

    public InterviewContainer(String id, InterviewNode parent, Split split, List<InterviewNode> nodes) {
        super(id, parent, split);
        this.nodes = nodes;
    }

    public List<InterviewNode> getNodes() {
        return nodes;
    }

    public void addNode(InterviewNode interviewNode){
        this.getNodes().add(interviewNode);
        refreshNodePathCache(this.getNodes());
    }

    public void setNodes(List<InterviewNode> nodes) {
        this.nodes = nodes;
    }

    public HashMap<String, InterviewNode> getInterviewPathCache() {
        return interviewPathCache;
    }

    public void setInterviewPathCache(HashMap<String, InterviewNode> interviewPathCache) {
        this.interviewPathCache = interviewPathCache;
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
