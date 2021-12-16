package com.dm.interview_service.model;

public class SplitRequestCounterparty {

    private String idToSplit;
    private InterviewNodePath nodePath;

    public SplitRequestCounterparty(String idToSplit, InterviewNodePath nodePath) {
        this.idToSplit = idToSplit;
        this.nodePath = nodePath;
    }

    public String getIdToSplit() {
        return idToSplit;
    }

    public void setIdToSplit(String idToSplit) {
        this.idToSplit = idToSplit;
    }

    public InterviewNodePath getNodePath() {
        return nodePath;
    }

    public void setNodePath(InterviewNodePath nodePath) {
        this.nodePath = nodePath;
    }
}
