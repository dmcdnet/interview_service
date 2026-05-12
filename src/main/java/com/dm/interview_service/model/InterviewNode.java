package com.dm.interview_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class InterviewNode {

    private String id;
    private Split split;
    private List<Split> splitHistory = new ArrayList<>();
    protected HashMap<String, InterviewNode> interviewPathCache;
    @JsonIgnore
    private InterviewNode parent;

    public InterviewNode() {}

    public InterviewNode(String id, InterviewNode parent) {
        this.parent = parent;
        this.id = id;
    }

    public InterviewNode(String id, InterviewNode parent, Split split) {
        this.parent = parent;
        this.id = id;
        this.split = split;
    }

    protected void refreshNodePathCache(List<InterviewNode> nodes){
        if(interviewPathCache == null){
            interviewPathCache = new HashMap<>();
        } else {
            interviewPathCache.clear();
        }
        nodes.forEach(n -> interviewPathCache.put(generateNodePathString(n), n));
    }

    private String generateNodePathString(InterviewNode node){
        if (node.getSplitHistory().isEmpty()) return node.getId();
        StringBuilder sb = new StringBuilder(node.getId());
        for (Split s : node.getSplitHistory()) {
            sb.append(switch (s.getSplitType()) {
                case SEQUENCE     -> "[SN=" + s.getSequenceNumber() + "]";
                case COUNTERPARTY -> "[CP]";
            });
        }
        return sb.toString();
    }

    public InterviewNode getNodeByNodePathReference(InterviewNodePath interviewNodePath){

        if(interviewPathCache.containsKey(interviewNodePath.getNodePath().get(0)) && interviewNodePath.getPathDepth()==1){
            return interviewPathCache.get(interviewNodePath.getNodePath().get(0));
        } else if(!interviewPathCache.containsKey(interviewNodePath.getNodePath().get(0))){
            return null;
        }

        return interviewPathCache.get(interviewNodePath.getNodePath().get(0)).getNodeByNodePathReference(InterviewNodePath.stripTopLevel(interviewNodePath));
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

    public InterviewNode getParent() {
        return parent;
    }

    public void setParent(InterviewNode parent) {
        this.parent = parent;
    }

    public void setSplit(Split split) {
        this.split = split;
    }

    public List<Split> getSplitHistory() {
        return splitHistory;
    }

    public void setSplitHistory(List<Split> splitHistory) {
        this.splitHistory = splitHistory;
    }

    public abstract InterviewNode childExists(String id);

    public abstract InterviewNode split(boolean splitRoot);

    @Override
    public String toString() {
        return "InterviewNode{" +
                "id='" + id + '\'' +
                ", split=" + split +
                '}';
    }
}
