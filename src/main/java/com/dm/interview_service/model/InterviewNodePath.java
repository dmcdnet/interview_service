package com.dm.interview_service.model;

import java.util.List;

public class InterviewNodePath {

    /*
    Path to uniquely reference a node in the tree similar to xPath in XML using _IDs_

    Split Types:
    SEQUENCE Example : /c1/g1/q1[SN=2]

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

    public Integer getPathDepth(){
        return nodePath.size();
    }

    public static InterviewNodePath stripTopLevel(InterviewNodePath node){
        if(node.getPathDepth()>1) {
            return new InterviewNodePath(node.getNodePath().subList(1, node.getPathDepth()));
        } else return null;
    }

}
