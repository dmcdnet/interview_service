package com.dm.interview_service.model;

import java.util.HashMap;
import java.util.List;

public abstract class InterviewNode {

    private String id;
    private Split split;
    protected HashMap<String, InterviewNode> interviewPathCache;

    public InterviewNode() {}

    public InterviewNode(String id) {
        this.id = id;
    }

    public InterviewNode(String id, Split split) {
        this.id = id;
        this.split = split;
    }

    protected void refreshNodePathCache(List<InterviewNode> nodes){
        if(interviewPathCache == null){
            interviewPathCache = new HashMap<>();
        } else {
            interviewPathCache.clear();
        }
        nodes.forEach(n -> {
            interviewPathCache.put(generateNodePathString(n), n);
        });
    }

    private String generateNodePathString(InterviewNode container){
        if(container.getSplit() != null &&
                container.getSplit().getSplitType().equals(SplitType.SEQUENCE) && container.getSplit().getSequenceNumber()>0){
            return container.getId()+"[SN="+container.getSplit().getSequenceNumber()+"]";
        } else {
            return container.getId();
        }
    }

    public InterviewNode getNodeByNodePathReference(InterviewNodePath interviewNodePath){
        String[] splitString = interviewNodePath.getNodePath().get(0).split("[\\[\\]]");

        if(interviewPathCache.containsKey(splitString[0]) && interviewNodePath.getPathDepth()==1){
            return interviewPathCache.get(splitString[0]);
        }

        return interviewPathCache.get(splitString[0]).getNodeByNodePathReference(InterviewNodePath.stripTopLevel(interviewNodePath));
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

    public abstract InterviewNode childExists(String id);

    @Override
    public String toString() {
        return "InterviewNode{" +
                "id='" + id + '\'' +
                ", split=" + split +
                '}';
    }
}
