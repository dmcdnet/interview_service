package com.dm.interview_service.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Interview {

    private List<InterviewContainer> nodes;
    private HashMap<String, InterviewContainer> interviewPathCache;
    private String id;
    private String name;

    public Interview() {
        this.nodes = new ArrayList<>();
    }

    public Interview(List<InterviewContainer> nodes) {
        this.nodes = nodes;
        refreshNodePathCache();
    }

    public InterviewContainer getNodeByID(String id){
        return nodes.stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
    }

    private void refreshNodePathCache(){
        if(interviewPathCache == null){
            interviewPathCache = new HashMap<>();
        } else {
            interviewPathCache.clear();
        }
        this.nodes.forEach(n -> {
            interviewPathCache.put(generateNodePathString(n), n);
        });
    }

    private String generateNodePathString(InterviewContainer container){
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
        } else if(!interviewPathCache.containsKey(splitString[0])){
            return null;
        }

        return interviewPathCache.get(splitString[0]).getNodeByNodePathReference(InterviewNodePath.stripTopLevel(interviewNodePath));
    }

    public List<InterviewContainer> getNodes() {
        return nodes;
    }

    public void addNode(InterviewContainer container){
        this.getNodes().add(container);
        refreshNodePathCache();
    }

    public void setNodes(List<InterviewContainer> nodes) {
        refreshNodePathCache();
        this.nodes = nodes;
    }

    public HashMap<String, InterviewContainer> getInterviewPathCache() {
        return interviewPathCache;
    }

    public void setInterviewPathCache(HashMap<String, InterviewContainer> interviewPathCache) {
        this.interviewPathCache = interviewPathCache;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Interview{" +
                "nodes=" + nodes +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
