package com.dm.interview_service.model;

public class SplitRequest {

    private String nodeId;
    private SplitType splitBy;
    private boolean split;  // true to split, false to remove split

    public SplitRequest(String nodeId, SplitType splitBy, boolean split) {
        this.nodeId = nodeId;
        this.splitBy = splitBy;
        this.split = split;
    }





}
