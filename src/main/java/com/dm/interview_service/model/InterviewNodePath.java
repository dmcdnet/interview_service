package com.dm.interview_service.model;

import java.util.List;

public class InterviewNodePath {

    /*
    Path to uniquely reference a node in the tree similar to xPath in XML

    Split Types:
    SEQUENCE Example : /clause1/group1/question1[SN=2]

    Qualifier not always required.

    */
    private List<String> nodePath;

    public InterviewNodePath(List<String> nodePath) {
        this.nodePath = nodePath;
    }

    public List<String> getNodePath() {
        return nodePath;
    }

    public void setNodePath(List<String> nodePath) {
        this.nodePath = nodePath;
    }
}
