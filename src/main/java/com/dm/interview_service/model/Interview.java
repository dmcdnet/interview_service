package com.dm.interview_service.model;

import java.util.ArrayList;
import java.util.List;

public class Interview {

    private List<InterviewContainer> nodes;
    private String id;
    private String name;

    public Interview() {
        this.nodes = new ArrayList<>();
    }

    public Interview(List<InterviewContainer> nodes) {
        this.nodes = nodes;
    }

    public InterviewContainer nodeExists(String id){
        return nodes.stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
    }

    public List<InterviewContainer> getNodes() {
        return nodes;
    }

    public void setNodes(List<InterviewContainer> nodes) {
        this.nodes = nodes;
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
