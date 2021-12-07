package com.dm.interview_service.model;

public class SplitRequestSequence extends SplitRequest {

    // true to add, false to remove
    private boolean add;

    public SplitRequestSequence(String idToSplit, InterviewNodePath nodePath, boolean add) {
        super(idToSplit, nodePath);
        this.add = add;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }
}
